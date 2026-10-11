FROM eclipse-temurin:17-jdk

# 更新软件包列表并安装所需的工具
RUN apt-get update && apt-get install -y procps


# 设置工作目录
WORKDIR /app

# 将你的脚本添加到容器中
ADD run_java.sh /sh/

# 洗掉 Windows 换行符（CRLF -> LF），否则 shebang 末尾的 \r 会让 Linux 报 no such file or directory
RUN sed -i 's/\r$//' /sh/run_java.sh

# 设置脚本可执行权限
RUN chmod +x /sh/run_java.sh

# 在容器启动时执行你的脚本
# CMD ["/app/hello.sh"]

