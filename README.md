# 校园博客 [scblogs]

<p align=center>
  <a href="http://www.scblogs.cn">
    <img src="./document/image/scblogs-logo.png" alt="校园博客">
  </a>
</p>

<p  align=center>
   基于微服务架构的前后端分离的博客系统
</p>
<p align="center">
<a target="_blank" href="https://github.com/stick-i/scblogs">
  <img src="https://img.shields.io/hexpm/l/plug.svg" alt=""/>
  <img src="https://img.shields.io/github/stars/stick-i/scblogs" alt=""/>
  <img src="https://img.shields.io/github/forks/stick-i/scblogs" alt=""/>
<br>
  <img src="https://img.shields.io/badge/SpringBoot-2.6.6-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloud-2021.0.2-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloudAlibaba-2021.0.1.0-green" alt=""/><br>
  <img src="https://img.shields.io/badge/Vue-2.6.14-green" alt=""/>
  <img src="https://img.shields.io/badge/ElementUI-2.15.6-green" alt=""/>
</a></p>

## 项目计划

陆续有一些开源贡献者愿意加入到这个项目当中了😁，为了便于大家参与贡献，我将项目计划做了一些整理，主要分为以下几个部分：

|         新功能或新模块         |  难度  |
| :----------------------------: | :----: |
| 对原来的模块进行完善及新增功能 | 看情况 |
|         管理员系统模块         |  中等  |
|            问答模块            |  简单  |
|            商城模块            |  中等  |
|             OJ模块             |  困难  |

另外详细的计划内容已经作为issue发在GitHub上了，访问链接：https://github.com/stick-i/scblogs/issues/64 即可查看，欢迎大家参与噢！

## 项目地址

项目托管在 **Gitee** 和 **GitHub** 上，欢迎大家 **Star**

- Gitee地址：https://gitee.com/sticki/scblogs
- GitHub地址：https://github.com/stick-i/scblogs
- QQ交流群：729931395

## 项目特点

- 微服务架构开发，友好的代码结构及注释，便于阅读及二次开发
- 前后端分离，通过 **Json** 进行数据交互，前端无需关注后端技术
- 采用 **Nacos** 作为服务注册中心，支持分布式集群架构，方便微服务配置管理
- 采用 **Getaway** 搭建网关服务，进行负载转发的同时对用户身份进行识别，并支持配置接口未认证拦截功能
- 采用 **JWT** 的方式进行用户鉴权，配合网关服务保证其他所有微服务都能准确识别用户身份
- 采用 **Minio** 搭建对象存储服务，同时引入七牛云对象存储
- 采用 **AOP** + 自定义注解 + **Redis** 实现限制IP接口访问次数
- 基于 **Redis** 实现的分布式全局唯一ID生成器，保障ID可用性、安全性。
- 页面交互使用 **Vue2.x**，极大的提高了开发效率

## 项目目录

```
├─config：全局配置文件目录
├─document：项目文档目录
├─common：公用模块
│  ├─common-amqp：公用AMQP模块
│  ├─common-core：核心模块
│  ├─common-redis：公用Redis模块
│  ├─common-regex：公用正则模块
│  ├─common-tool：公用工具模块（需要修改或调整）
│  └─common-web：公用web模块
├─gateway-service：网关服务
├─user-service：用户服务
├─blog-service：博客服务
├─blink-service：动态服务
├─comment-service：评论服务
├─message-service：消息服务
├─resource-service：资源服务
└─vueblog：前端Vue服务
```

## 技术选型

### 后端技术

|      技术      |       说明       |                       官网                        |
| :------------: | :--------------: | :-----------------------------------------------: |
|   SpringBoot   |     MVC框架      |      https://spring.io/projects/spring-boot       |
|  SpringCloud   |    微服务框架    |     https://spring.io/projects/spring-cloud/      |
|    Gateway     |   网关服务框架   |  https://spring.io/projects/spring-cloud-gateway  |
|     Fegin      |     RPC框架      | https://spring.io/projects/spring-cloud-openfeign |
|     Nacos      |  注册、配置中心  |              https://nacos.io/zh-cn/              |
|  MyBatis-Plus  |    数据库框架    |             https://mp.baomidou.com/              |
|     MySQL      |      数据库      |              https://www.mysql.com/               |
|     Redis      |    分布式缓存    |                 https://redis.io/                 |
|    RabbitMQ    |     消息队列     |             https://www.rabbitmq.com/             |
| Elasticsearch  |     搜索引擎     |      https://github.com/elastic/elasticsear       |
|     Docker     |    容器化部署    |              https://www.docker.com/              |
|    Jenkins     |  自动化部署服务  |              https://www.jenkins.io/              |
|     Druid      |   数据库连接池   |         https://github.com/alibaba/druid          |
|     七牛云     |      七牛云      |   https://developer.qiniu.com/sdk#official-sdk    |
|     SLF4J      |     日志框架     |               http://www.slf4j.org/               |
|     Lombok     | 简化对象封装工具 |      https://github.com/rzwitserloot/lombok       |
|     Nginx      |    web服务器     |                 http://nginx.org/                 |
|     Hutool     |  Java工具包类库  |             https://hutool.cn/docs/#/             |
|     Minio      | 本地对象存储服务 |                  https://min.io/                  |
| Docker Compose |  Docker容器编排  |         https://docs.docker.com/compose/          |

### 前端技术


### 后端运行
**1、下载nacos**
访问nacos的github地址下载：https://github.com/alibaba/nacos/releases。
(PS：mac电脑的同学特别是M1芯片的建议下载低版本(1.4.4)的高版本会有问题。或者直接用项目里面的(nacos-server-1.4.4.zip)，解压出来以后没有问题)

**2、本地将Nacos运行起来**
```shell

# 进入到nacos的bin目录
cd /nacos/bin

# 运行nacos
sh startup.sh -m standalone

# 停止nacos
sh shutdown.sh
# 查看nacos的运行日志
cat ../logs/start.out


         ,--.
       ,--.'|
   ,--,:  : |                                           Nacos 1.4.4
,`--.'`|  ' :                       ,---.               Running in stand alone mode, All function modules
|   :  :  | |                      '   ,'\   .--.--.    Port: 8848
:   |   \ | :  ,--.--.     ,---.  /   /   | /  /    '   Pid: 32084
|   : '  '; | /       \   /     \.   ; ,. :|  :  /`./   Console: http://192.168.0.105:8848/nacos/index.html
'   ' ;.    ;.--.  .-. | /    / ''   | |: :|  :  ;_
|   | | \   | \__\/: . ..    ' / '   | .; : \  \    `.      https://nacos.io
'   : |  ; .' ," .--.; |'   ; :__|   :    |  `----.   \
|   | '`--'  /  /  ,.  |'   | '.'|\   \  /  /  /`--'  /
'   : |     ;  :   .'   \   :    : `----'  '--'.     /
;   |.'     |  ,     .-./\   \  /            `--'---'
'---'        `--`---'     `----'

2022-10-11 23:29:05,572 INFO Tomcat initialized with port(s): 8848 (http)

2022-10-11 23:29:05,778 INFO Root WebApplicationContext: initialization completed in 1531 ms

2022-10-11 23:29:07,591 INFO Adding welcome page: class path resource [static/index.html]

2022-10-11 23:29:07,842 WARN You are asking Spring Security to ignore Ant [pattern='/**']. This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.

2022-10-11 23:29:07,842 INFO Will secure Ant [pattern='/**'] with []

2022-10-11 23:29:07,860 INFO Will not secure any request

2022-10-11 23:29:07,873 INFO Exposing 1 endpoint(s) beneath base path '/actuator'

2022-10-11 23:29:07,990 INFO Tomcat started on port(s): 8848 (http) with context path '/nacos'

2022-10-11 23:29:08,003 INFO Nacos started successfully in stand alone mode. use embedded storage

2022-10-11 23:29:27,506 INFO Initializing Servlet 'dispatcherServlet'

2022-10-11 23:29:27,508 INFO Completed initialization in 1 ms

# 访问nacos控制台
 http://192.168.0.105:8848/nacos/index.html
```
**3、导入nacos配置**

参考：document->nacos_config->REAMED.md文档

**4、启动gateway-service服务**

运行GatewayServerApplication，其他模块也跟网关模块一样运行即可。

### 前端运行

**1、安装node.js**
参考菜鸟教程：https://www.runoob.com/nodejs/nodejs-install-setup.html

**2、运行前端项目**
```shell

# 进入到前端项目目录
cd vueblog

# 强烈建议不要用直接使用 cnpm 安装，会有各种诡异的 bug，可以通过重新指定 registry 来解决 npm 安装速度慢的问题。
npm install --registry=https://registry.npmmirror.com

# 本地开发 启动项目
npm run serve
 
```
打开浏览器输入
http://localhost:8080/