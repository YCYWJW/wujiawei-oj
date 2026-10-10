# wujiawei 编程在线学习平台

## 项目简介

一个基于 SpringBoot + Vue3 的前后端分离在线编程学习平台，集在线做题、编程竞赛、即时通讯、文章创作、视频教程、技术论坛六大模块于一体，为编程学习者提供一站式的学习环境。

## 技术栈

**后端**：SpringBoot 2.7.2、MyBatis-Plus、MySQL 8.0、Redis、RabbitMQ、ElasticSearch、Netty、JWT
**前端**：Vue3、TypeScript、Element Plus
**中间件**：Docker（代码沙箱隔离）、Cpolar（内网穿透）

## 核心功能

- 题目中心：支持题目浏览、搜索、分类、在线提交、代码判题，判题沙箱基于 Docker 隔离，保证安全性。
- 编程竞赛：支持周赛、在线 PK、实时排行榜。
- 即时通讯：基于 Netty 的 WebSocket 实现好友私聊、群聊。
- 技术论坛：帖子、评论、点赞、收藏。
- 课程模块：视频教程、章节管理、进度追踪。
- 用户中心：注册登录、资料管理、信用积分、竞赛记录。

## 项目亮点

1. 代码沙箱：基于 Docker 实现多语言代码隔离执行，防止恶意代码影响宿主机。
2. Redis 缓存优化：排行榜、会话信息、用户信息、聊天房间/群成员、点赞记录走 Redis 缓存。
3. 消息队列削峰：判题任务、消息推送走 RabbitMQ，避免高并发下服务雪崩。

## 项目结构

```
wujiawei-oj/                          父工程（Maven 聚合 POM）
├── pom.xml                           聚合 tx-oj 与 tx-code-sandbox 两个子模块
├── tx-oj/                            主后端服务（SpringBoot 单体应用）
│   ├── pom.xml
│   ├── Dockerfile
│   ├── mvnw / mvnw.cmd               Maven Wrapper
│   ├── config-sample/                配置模板（application.yml / -prod / -test）
│   ├── sql/
│   │   ├── wujiawei_oj_ad.sql        建库建表脚本
│   │   └── es_mapping                帖子索引的 ES mapping
│   ├── doc/swagger.png
│   ├── scripts/app.sh                部署脚本
│   └── src/main/
│       ├── java/com/wujiawei/oj/
│       │   ├── MainApplication.java  启动类
│       │   ├── controller/           REST 接口层
│       │   │   ├── course/           课程模块接口
│       │   │   └── forum/            论坛模块接口
│       │   ├── judge/                判题核心
│       │   │   ├── codesandbox/      远程代码沙箱客户端（工厂 / 代理）
│       │   │   └── strategy/         分语言判题策略
│       │   ├── chat/                 即时通讯模块
│       │   │   ├── websocket/        Netty WebSocket 服务端
│       │   │   ├── consumer/         RabbitMQ 消费者（消息落库 / WS 推送）
│       │   │   ├── service/          房间、会话、消息推送业务
│       │   │   └── domain/ DTO / VO
│       │   ├── service/              业务层（impl / business / adapter / cache）
│       │   ├── mapper/               MyBatis-Plus Mapper
│       │   ├── model/                实体 / DTO / VO / 枚举
│       │   ├── esdao/                帖子 ElasticSearch DAO
│       │   ├── job/                  定时任务（cycle / match / once）
│       │   ├── config/               全局配置（MQ / Redis / WebSocket 等）
│       │   ├── aop/ annotation/      自定义注解与切面
│       │   ├── event/ listener/      事件发布与监听
│       │   ├── manager/ utils/       通用工具
│       │   └── exception/            全局异常处理
│       └── resources/
│           ├── application.yml       主配置（本地文件，已被 .gitignore 忽略）
│           ├── mapper/               MyBatis XML 映射
│           └── META-INF/
└── tx-code-sandbox/                  独立的代码沙箱服务
    ├── pom.xml
    ├── scripts/                      各语言运行脚本模板（c / java / js / python）
    └── src/main/java/com/wujiawei/txcodesandbox/
```

## 本地部署

### 一、环境要求

本地运行需要准备以下软件，并保证版本兼容：

| 软件 | 版本 | 说明 |
| --- | --- | --- |
| JDK | 17 | 项目使用 Java 17 编译 |
| Maven | 3.9+ | 打包 / 启动工具 |
| MySQL | 8.0 | 用户 `root`，密码自定（与下文配置保持一致） |
| Redis | — | 本地启动，无密码，使用 `database 2` |
| RabbitMQ | 3.x | 用户 `admin` / 密码请自行设置密码，vhost `/`，需装延迟消息插件 |
| Elasticsearch | 7.x | 无需手工建索引，框架首次写入自动创建 |

各中间件默认端口：MySQL `3306`、Redis `6379`、RabbitMQ `5672`（管理台 `15672`）、Elasticsearch `9200`。

### 二、初始化数据库

**2.1 启动 MySQL 并创建数据库**

SQL 脚本里已自带 `CREATE DATABASE wujiawei_oj_ad` 语句，因此无需手动建库，直接导入即可。

**2.2 导入 SQL 脚本**

```bash
mysql -uroot -p --default-character-set=utf8mb4 < tx-oj/sql/wujiawei_oj_ad.sql
```

> 注意：务必带上 `--default-character-set=utf8mb4`，否则中文内容会乱码；`-p` 后面跟本机 MySQL 的 `root` 密码。

### 三、准备中间件

**3.1 Redis**

本地启动 Redis（无密码）即可，项目默认连接 `127.0.0.1:6379`、`database 2`。

**3.2 RabbitMQ（Docker 启动 + 装延迟插件 + 建 admin 用户）**

项目依赖 RabbitMQ 的延迟消息插件 `rabbitmq_delayed_message_exchange`，并固定使用 `admin` 账号和 `/` vhost（密码请自行设置），需一次准备到位：

```bash
# 1. 用 Docker 启动（带管理台）
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# 2. 安装延迟消息插件，然后重启容器
docker exec rabbitmq rabbitmq-plugins enable rabbitmq_delayed_message_exchange
docker restart rabbitmq

# 3. 创建 admin 用户并授权 / vhost
docker exec rabbitmq rabbitmqctl add_user admin 你的密码
docker exec rabbitmq rabbitmqctl set_user_tags admin administrator
docker exec rabbitmq rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"
```

**3.3 Elasticsearch**

启动 Elasticsearch 即可，无需手工建索引；帖子索引 `post` 会在首次写入时由框架自动创建。

```bash
docker run -d --name es -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" elasticsearch:7.17.10
```

### 四、修改配置文件

**4.1 复制配置模板**

项目真实配置文件 `application.yml` 已被 `.gitignore` 忽略（不随仓库提交），克隆后需手动从模板复制一份：

```bash
cp tx-oj/config-sample/application.yml tx-oj/src/main/resources/application.yml
```

**4.2 替换连接信息**

打开 `tx-oj/src/main/resources/application.yml`，核对并修改以下几项（模板里中间件账号密码是占位值，请改成和上面准备的环境一致）：

- `spring.datasource.url`：数据库名保持 `wujiawei_oj_ad`，用户名 / 密码填你 MySQL 的账号
- `spring.redis`：`host` / `port` / `database: 2`；无密码时把 `password` 行注释或删掉
- `spring.rabbitmq`：`username: admin`、`virtual-host: /`，`password` 填你在 3.2 里设置的密码
- `spring.elasticsearch.uris`：`http://localhost:9200`

**4.3 （可选）开启真实 AI 摘要**

AI 摘要默认走 Mock 占位（`ai-summary.enabled: false`）。要接入真实 DeepSeek 摘要，推荐新建
`tx-oj/src/main/resources/application-local.yml`（该文件已被 `.gitignore` 忽略，不会提交到仓库）：

```yaml
ai-summary:
  enabled: true
  deepseek-api-key: "你的 DeepSeek Key"
```

然后以 `--spring.profiles.active=local` 启动；也可以把 Key 留空，改为从环境变量
`DEEPSEEK_API_KEY` 读取。**不要把真实 Key 写进被 Git 跟踪的 application.yml。**

### 五、启动后端

**5.1 直接运行（开发方式）**

```bash
cd tx-oj
mvn spring-boot:run
```

（也可以 `mvn clean package -DskipTests` 打成 jar 后 `java -jar` 运行，效果相同。）

**5.2 访问验证**

浏览器打开接口文档，能正常加载即说明启动成功：

- 接口文档（Knife4j）：http://localhost:8121/api/doc.html

### 六、常见问题排查

| 现象 | 原因 | 处理 |
| --- | --- | --- |
| 启动报 `unknown exchange type 'x-delayed-message'` | RabbitMQ 延迟插件没装 | 执行上文 3.2 的插件安装命令并重启容器 |
| 启动报 `Unknown database 'wujiawei_oj_ad'` | SQL 没导入 | 按上文 2.2 重新导入 |
| 数据库中文乱码 | 导入编码不是 utf8mb4 | 带 `--default-character-set=utf8mb4` 重新导入 |
| MQ 队列名带 `.prod` 后缀 | 源码里 profile 被硬编码为 `prod` | 正常现象，无需处理 |
| doc.html 打不开 | 端口被占或路径少了前缀 | 确认 8121 端口空闲，访问路径要带 `/api` 前缀 |

## 作者

李嘉图（Ricardo）

- GitHub: <https://github.com/YCYWJW>
- Email: 3505498783@qq.com