## Docker 安装 MySQL 8.0.32

`````bush
# 拉取镜像
docker pull mysql:8.0.32
# 查看镜像
docker images
# 创建挂载目录(-p 如果存在就不创建，不存在才创建)
mkdir -p /Users/dengyong/data/mysql/conf
mkdir -p /Users/dengyong/data/mysql/data
mkdir -p /Users/dengyong/data/mysql/logs
# 启动镜像( --lower_case_table_names=1 ：忽略大小写 )
docker run \
--restart=always \
--privileged=true \
--name mysql \
-p 3306:3306 \
-v /Users/dengyong/data/mysql/conf:/etc/mysql/conf.d \
-v /Users/dengyong/data/mysql/data:/var/lib/mysql \
-v /Users/dengyong/data/mysql/logs:/var/logs \
-e MYSQL_ROOT_PASSWORD=hihcxcn \
-d mysql:8.0.32 \
--lower_case_table_names=1
# 启动参数说明
````
-p 设置docker容器映射端口
--restart=always 设置mysql容器跟随docker自启动
–name 设置docker容器名称
–privileged=true 设置MySQL 的root用户权限, 否则外部不能使用root用户登陆，true为允许root登录
-v /data/mysql/log:/var/log/mysql 挂载mysql日志节点
-v /data/mysql/data:/var/lib/mysql 挂载mysql数据节点
-v /data/mysql/conf:/etc/mysql/conf.d 挂载mysql服务配置文件节点
-e MYSQL_ROOT_PASSWORD=123456 设置mysql数据库root用户初始密码
`````

## Docker 安装 nacos 2.2.0

`````bush
# 拉取镜像
docker pull nacos/nacos-server:v2.2.0
# 创建主机映射文件
mkdir -p /Users/dengyong/data/nacos/logs/ -p /Users/dengyong/data/nacos/config -p /Users/dengyong/data/nacos/data
# 启动nacos
docker run \
--name nacos -d \
-p 8848:8848 \
-p 7848:7848 \
-p 9848:9848 \
-p 9849:9849 \
--privileged=true \
--restart=always \
-e JVM_XMS=256m \
-e JVM_XMX=256m \
-e MODE=standalone \
-e PREFER_HOST_MODE=hostname \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=127.0.0.1 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_DB_NAME=nacos \
-e MYSQL_SERVICE_USER=hihcxcn \
-e MYSQL_SERVICE_PASSWORD=123456 \
-v /Users/dengyong/data/nacos/logs:/home/nacos/logs \
-v /Users/dengyong/data/nacos/conf/application.properties:/home/nacos/conf/application.properties \
-v /Users/dengyong/data/nacos/data:/home/nacos/data \
nacos/nacos-server:v2.2.0
# 参数说明
````
docker启动容器
docker run \
容器名称叫nacos -d后台运行
--name nacos -d \
nacos默认端口8848 映射到外部端口8848
-p 8848:8848 \
naocs 应该是2.0版本以后就需要一下的两个端口 所以也需要开放
-p 9848:9848 
-p 9849:9849 
--privileged=true \
docker重启时 nacos也一并重启
--restart=always \
-e 配置 启动参数
配置 jvm
-e JVM_XMS=256m 
-e JVM_XMX=256m \
单机模式
-e MODE=standalone 
-e PREFER_HOST_MODE=hostname \
数据库是mysql 配置持久化 不使用nacos自带的数据库
-e SPRING_DATASOURCE_PLATFORM=mysql \
写自己的数据库地址
-e MYSQL_SERVICE_HOST=###### \
数据库端口号
-e MYSQL_SERVICE_PORT=3306 \
mysql的数据库名称
-e MYSQL_SERVICE_DB_NAME=nacos \
mysql的账号密码
-e MYSQL_SERVICE_USER=root 
-e MYSQL_SERVICE_PASSWORD=root \
-v 映射docker内部的文件到docker外部 我这里将nacos的日志 数据 以及配置文件 映射出来
映射日志
-v /root/apply/docker/apply/nacos/logs:/home/nacos/logs \
映射配置文件 (应该没用了 因为前面已经配置参数了)
-v /root/apply/docker/apply/nacos/init.d/custom.properties:/etc/nacos/init.d/custom.properties \
映射nacos的本地数据 也没啥用因为使用了mysql
-v /root/apply/docker/apply/nacos/data:/home/nacos/data \
启动镜像名称:版本号
nacos/nacos-server:v2.2.0
````
# 访问nacos，默认账号密码都是`nacos`
https://XXX.XXX.XXX.XXX:8848/nacos
`````

## Docker 安装 Redis 6.0.9

`````bush

