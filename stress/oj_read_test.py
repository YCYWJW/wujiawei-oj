"""任务2-A：OJ 读接口压测 —— POST /api/question/list（题目分页查询）。

这个接口全程只走 MySQL + MyBatis-Plus，不碰 Elasticsearch、不碰 RabbitMQ，
所以它的压测数据是 100% 真实的。接口没有 @AuthCheck 注解，属于公开接口，不用登录。

用法（在 D:/code/txing-oj-backend 目录下运行）：
    "D:/RAG-Knowledge-Base/backend/venv/Scripts/python.exe" -m locust \
      -f stress/oj_read_test.py --headless --host http://127.0.0.1:8121 \
      -u 50 -r 5 --run-time 1m --csv=stress/result_oj_read --only-summary

-u 50  = 50 并发；-r 5 = 每秒爬坡 5 个用户（10 秒爬满）；--run-time 1m = 持续 1 分钟
"""
from locust import HttpUser, between, task

# 分页查询请求体：对应后端 utils/page/PageVO 的结构
# filter=过滤条件（空数组=不加筛选）、page=第几页每页几条、sorts=排序（空数组=默认排序）
LIST_BODY = {
    "filter": [],
    "page": {"current": 1, "pageSize": 10},
    "sorts": [],
}


class OJReadUser(HttpUser):
    """只打题目列表接口的虚拟用户。"""

    host = "http://127.0.0.1:8121"
    # 压测时不加思考时间，测接口本身的极限吞吐；想模拟真人可改成 between(0.5, 2)
    wait_time = between(0, 0)

    @task
    def question_list(self):
        """题目分页查询：200 且返回体里带 data 才算成功。"""
        with self.client.post(
            "/api/question/list",
            json=LIST_BODY,
            name="/api/question/list",
            catch_response=True,
        ) as r:
            if r.status_code == 200:
                # R.ok().put("data", page) —— 有 data 字段说明业务查询真的走通了
                if '"data"' in r.text:
                    r.success()
                else:
                    r.failure("返回 200 但没有 data 字段，业务可能异常")
            else:
                r.failure(f"状态码 {r.status_code}")