"""任务2-B：OJ 写接口压测 —— POST /api/question/submit/do（提交作答）。

【为什么这个接口只能低并发压】
提交接口做的事是：写一条提交记录到 MySQL → 把判题任务丢进 RabbitMQ → 立刻返回。
真正的判题由消息队列的消费者异步拉走、再调用远程代码沙箱（8082）执行。
所以高并发压它，风险不在这个 HTTP 接口本身，而在下游的 MQ 堆积和沙箱被压垮。
本脚本按「低并发兜底验证」定位使用，只证明接口在并发下不崩、能正常返回提交 ID。

【接口要求登录】
do() 上有 @AuthCheck(mustRole = "login")，认证方式不是标准的 Authorization 头，
而是自定义请求头 TOKEN（见 aop/AuthInterceptor.java：request.getHeader("TOKEN")）。
token 由 /api/user/login 签发，同时存进 Redis（5 天有效），校验时会和 Redis 里的比对。

用法（在 D:/code/txing-oj-backend 目录下运行）：
    OJ_ACCOUNT=压测账号 OJ_PASSWORD=密码 OJ_QUESTION_ID=题目ID \
      "D:/RAG-Knowledge-Base/backend/venv/Scripts/python.exe" -m locust \
      -f stress/oj_write_test.py --headless --host http://127.0.0.1:8121 \
      -u 10 -r 2 --run-time 30s --csv=stress/result_oj_write --only-summary
"""
import os

from locust import HttpUser, between, task

ACCOUNT = os.getenv("OJ_ACCOUNT", "")
PASSWORD = os.getenv("OJ_PASSWORD", "")
QUESTION_ID = int(os.getenv("OJ_QUESTION_ID", "1"))
# 思考时间：两次提交之间的间隔秒数。默认 0（打满吞吐）；
# 做「低并发兜底验证」时设成 3，让每个虚拟用户发完等 3 秒，控制写入总量。
WAIT = float(os.getenv("OJ_WAIT", "0"))

# 固定提交一句最简单的代码：只验证提交链路通不通，不关心判题结果
CODE = 'System.out.println("ok");'
LANGUAGE = "java"


class OJWriteUser(HttpUser):
    """提交作答的低并发虚拟用户。"""

    host = "http://127.0.0.1:8121"
    wait_time = between(WAIT, WAIT)

    def on_start(self):
        """准备工作：登录拿 TOKEN（on_start 的请求不计入统计表）。"""
        self.token = None
        with self.client.post(
            "/api/user/login",
            json={"userAccount": ACCOUNT, "userPassword": PASSWORD},
            name="[准备]登录",
            catch_response=True,
        ) as r:
            if r.status_code == 200:
                body = r.json()
                # 后端把 token 放在 data 里返回（LoginUserVO.token）
                data = body.get("data") or {}
                self.token = data.get("token") or body.get("token")
                if self.token:
                    r.success()
                else:
                    r.failure("登录成功但返回体里没有 token 字段")
            else:
                r.failure(f"登录失败(状态码 {r.status_code})")

    @task
    def submit(self):
        """提交作答：拿到提交 ID 就算成功（判题是异步的，不在本接口范围内）。"""
        if not self.token:
            return

        with self.client.post(
            "/api/question/submit/do",
            json={"language": LANGUAGE, "code": CODE, "questionId": QUESTION_ID},
            headers={"TOKEN": self.token},
            name="/api/question/submit/do",
            catch_response=True,
        ) as r:
            # 成功的判定要看业务码：这个后端 R.ok() 返回的 code 是 0（不是 HTTP 的 200）
            if r.status_code == 200 and '"code":0' in r.text.replace(" ", ""):
                r.success()
            elif r.status_code == 200:
                # HTTP 通了但业务码不是 0，说明被业务校验挡下了（如语言非法、题目不存在）
                r.failure(f"业务失败：{r.text[:120]}")
            else:
                r.failure(f"状态码 {r.status_code}")