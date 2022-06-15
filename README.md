# 校园博客 [scblogs]

<p style="text-align:center">
  <a href="http://www.scblogs.cn">
    <img src="./document/image/scblogs-logo.png" alt="校园博客">
  </a>
</p>

<p style="text-align:center">
   基于微服务架构的前后端分离的博客系统
</p>
<p style="text-align:center">
<a target="_blank" href="https://github.com/stick-i/scblogs">
  <img src="https://img.shields.io/hexpm/l/plug.svg" alt=""/>
  <img src="https://img.shields.io/badge/JDK-1.8-green.svg" alt=""/>
  <img src="https://img.shields.io/badge/Springboot-2.6.6-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloud-2021.0.2-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloudAlibaba-2021.0.1.0-green" alt=""/>
  <img src="https://img.shields.io/badge/Vue-2.6.14-green" alt=""/>
  <img src="https://img.shields.io/badge/ElementUI-2.15.6-green" alt=""/>
</a></p>

## 项目特点

- 微服务架构开发，友好的代码结构及注释，便于阅读及二次开发
- 各服务所使用的数据库完全分离，且不相互调用
- 前后端分离，通过 **Json** 进行数据交互，前端无需关注后端技术
- 采用 **Nacos** 作为服务注册中心，支持分布式集群架构，方便微服务配置管理
- 采用 **Getaway** 搭建网关服务，进行负载转发的同时对用户身份进行识别，并支持配置接口未认证拦截功能
- 采用 **JWT** 的方式进行用户鉴权，配合网关服务保证其他所有微服务都能准确识别用户身份
- 采用 **Minio** 搭建对象存储服务，同时引入七牛云对象存储
- 采用 **AOP** + 自定义注解 + **Redis** 实现限制IP接口访问次数

- 页面交互使用 **Vue2.x**，极大的提高了开发效率。

## 项目地址

项目托管在 **Gitee** 和 **GitHub** 上，欢迎大家 **Star**

- Gitee地址：https://gitee.com/sticki/scblogs
- GitHub地址：https://github.com/stick-i/scblogs

## 项目目录

## 技术选型

### 后端技术

### 前端技术
