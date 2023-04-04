
# lovePigService

## 介绍
小小开发独立开发的恋爱小程序，开发投入大量精力。虽然没有用起来，但是每每看起来自己写的东西就这样尘封起来不如贡献给大家。祝大家心想事成，爱情事业双丰收。

## 前端项目地址
 **项目运行效果请看前端项目** 
[https://gitee.com/jokeo/lovePigUni](https://gitee.com/jokeo/lovePigUni)

## TODO
- [ ] 倒计时支持农历
- [ ] 纪念日通知-邮箱
- [ ] 纪念日通知-小程序通知


## 软件功能
查看项目目录下的love.xmind
主要实现了
- 恋爱倒计时
- 动态
- 纪念日
- 留言
- 承诺
- 相册

## 软件架构
| 框架                                                                                          | 说明               | 版本          | 学习指南                                                           |
|---------------------------------------------------------------------------------------------|------------------|-------------|----------------------------------------------------------------|
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | 应用开发框架           | 2.7.8       | [文档](https://github.com/YunaiV/SpringBoot-Labs)                |
| [MySQL](https://www.mysql.com/cn/)                                                          | 数据库服务器           | 5.7 / 8.0+  |                                                                |
| [Druid](https://github.com/alibaba/druid)                                                   | JDBC 连接池、监控组件    | 1.2.16   | [文档](http://www.iocoder.cn/Spring-Boot/datasource-pool/) |
| [MyBatis Plus](https://mp.baomidou.com/)                                                    | MyBatis 增强工具包    | 3.5.3   | [文档](http://www.iocoder.cn/Spring-Boot/MyBatis/)         |
| [Redis](https://redis.io/)                                                                  | key-value 数据库    | 5.0 / 6.0   |                                                                |
| [Redisson](https://github.com/redisson/redisson)                                            | Redis 客户端        | 3.18.0      | [文档](http://www.iocoder.cn/Spring-Boot/Redis/)           |
| [Spring MVC](https://github.com/spring-projects/spring-framework/tree/master/spring-webmvc) | MVC 框架           | 5.3.24      | [文档](http://www.iocoder.cn/SpringMVC/MVC/)               |
| [Hibernate Validator](https://github.com/hibernate/hibernate-validator)                     | 参数校验组件           | 6.2.5       | [文档](http://www.iocoder.cn/Spring-Boot/Validation/)      |
| [Lombok](https://projectlombok.org/)                                                        | 消除冗长的 Java 代码    | 1.18.24     | [文档](http://www.iocoder.cn/Spring-Boot/Lombok/)          |
| [腾讯cos](https://cloud.tencent.com/product/cos?from=20077&from_column=20077)                                                        | 免费10g储存图片    | 5.6.136 | [文档](https://cloud.tencent.com/document/product/436/10199)          |
| [weixin-java-miniapp](https://github.com/binarywang/weixin-java-miniapp-demo)                                                        | 方便对接微信小程序    | 4.4.0  | [文档](https://github.com/binarywang/weixin-java-miniapp-demo)          |
| [java-jwt](https://jwt.io/)                                                        | jwt权限认证    | 4.3.0  | [文档](https://jwt.io/)          |
| [fastjson](https://github.com/alibaba/fastjson/wiki/Quick-Start-CN)                                                        | 最快的json处理工具   | 2.0.25    | [文档](https://github.com/alibaba/fastjson/wiki/Quick-Start-CN)          |
| [emoji-java](https://github.com/vdurmont/emoji-java)                                                        | 表情报转换工具    | 5.1.1     | [文档](https://github.com/vdurmont/emoji-java)          |


## 安装教程
1.  拉代码
2.  创建名为lovepig的数据库（注意使用utf8mb4字符集，存储小表情需要）
3.  导入项目中src/main/resources/sql/love-pig.sql的sql
4.  按照提示修改配置文件application.yml，application-dev.yml（开发），application-prod.yml（生产）
5.  运行项目默认端口5210




### 配置文件修改说明
项目一共三个配置文件application.yml，application-dev.yml（开发），application-prod.yml（生产）
三个都需要配置，搜索内容为todo开头的都是必须配置的否则项目跑不起来
![输入图片说明](doc/%E9%85%8D%E7%BD%AE%E6%95%99%E7%A8%8B.png)

#### 必要环境准备
准备文档可以参考项目架构提供的文档地址
1. redis环境
2. mysql环境
3. 微信小程序环境
4. 腾讯云cos

#### 非必要配置
1. mail邮箱服务


## 使用说明
项目继承swagger，访问地址为：
http://127.0.0.1:5210/swagger-ui/index.html

## 参与贡献
1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
