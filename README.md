# 校园博客 [scblogs]

<p align=center>
  <a href="http://www.scblogs.cn">
    <img src="http://image.scblogs.cn/scblogs-logo.png" alt="校园博客">
  </a>
</p>

<p align=center>
   基于微服务架构的前后端分离的博客系统
</p>
<p align="center">
<a target="_blank" href="https://github.com/stick-i/scblogs">
  <img src="https://img.shields.io/badge/JDK-1.8-green.svg" alt=""/>
  <img src="https://img.shields.io/badge/Springboot-2.6.6-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloud-2021.0.2-green" alt=""/>
  <img src="https://img.shields.io/badge/SpringCloudAlibaba-2021.0.1.0-green" alt=""/>
  <img src="https://img.shields.io/badge/Vue-2.6.14-green" alt=""/>
  <img src="https://img.shields.io/badge/ElementUI-2.15.6-green" alt=""/>
</a></p>

## 项目特点

- 微服务架构开发，友好的代码结构及注释，便于阅读及二次开发
- 前后端分离，通过 **Json** 进行数据交互，前端无需关注后端技术
- 引入**RabbitMQ** 消息队列，用于邮件发送、更新 **Redis** 和 **Solr**
- 采用Minio搭建对象存储服务，同时引入七牛云对象存储
- 引入**ElasticSearch** 和 **Solr** 作为全文检索服务，并支持可插拔配置
- 采用 **Nacos** 作为服务发现和配置中心，轻松完成项目的配置的维护
- 引入 **Zipkin** 链路追踪，聚合各业务系统调用延迟数据，可以一眼看出延迟高的服务
- 采用 **AOP** + 自定义注解 + **Redis** 实现限制IP接口访问次数
- 采用 **Sentinel** 流量控制框架，通过配置再也不怕网站被爆破

- 页面交互使用 **Vue2.x**，极大的提高了开发效率。

## 项目目录

