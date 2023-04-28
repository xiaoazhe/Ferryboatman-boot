# Ferryman-boot



####  Spring Boot(版本) + Vue

环境部署操作
https://blog.csdn.net/weixin_44045059/article/details/121723799


Spring Cloud版 https://gitee.com/chongzhe/Ferryman-cloud

前台前端源代码
[https://gitee.com/chongzhe/Ferryman-blog](http://)

后台前端源代码
[https://gitee.com/chongzhe/Ferryboatman-admin](http://)

GitHub
[https://github.com/xiaoazhe/Ferryman-cloud](http://)

## 平台简介



## 后台系统内置功能

#### 系统管理
前端源码：https://gitee.com/chongzhe/Ferryboatman-admin
预览：http://admin.ferryboat.top/
1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 机构管理：配置系统组织机构，树结构展现支持数据权限。
3. 通知管理：通知管理。
4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
#### 博客管理
6. 分类管理：博客分类进行维护。
7. 内容管理：博客进行维护。
8. 评论管理：博客进行维护。
9. 音乐管理：前台浏览时候音乐播放。
10. 前台用户：前台用户管理。
#### 图片管理
11. 人脸识别列表：图库管理，也是人脸识别登录映射。
#### 系统运营
12. 导航管理：导航系统的管理，源码：https://gitee.com/chongzhe/Ferryboatman-nav
    预览：http://nav.ferryboat.top/
13. 友链管理：博客进行维护 。
14. 博客问答: 博客进行维护。
15. 资料管理：博客进行维护。
16. 闲聊管理：博客进行维护。
17. 文章爬取：单篇、批量，csdn、博客园、oschain等博客，一键 拿来吧你。只用来搬运自己文章和转发
#### 配置管理
18. 字典管理：字典管理。
19. 系统配置：账户配置 & 阿里云文件上传配置 & 七牛云文件上传配置，系统的各种文件上传的存储路径配置
#### 系统监控
20. 在线用户：在线用户。
21. 登录日志：登录日志
22. 操作日志：操作日志。
23. 数据监控：数据库监控。
24. 接口文档：丝袜哥。
#### 其他
25. 系统备份SQL，还原
26. 后台主题切换
27. 功能搜索
28. 人脸识别登录
29. 短信发送
30. 邮件发送
31. 死信队列延迟消息
32. JS拉取摄像头拍照
33. 微信服务号定时通知（无场景demo）
34. 百度站长推送

## 前台系统内置功能
前端源码：https://gitee.com/chongzhe/Ferryman-blog
预览：http://ferryboat.top/
1. 博客：搜索，关注，查看，评论，回复，发布
2. 提问：分类提问，回复，点赞，评论
3. 匿名吐槽：发布吐槽，评论
4. 资料链接：搜索自己想要的资料，开源~~白嫖
5. 友链：推荐
6. 个人中心：登录后的 发布记录，评论记录，浏览记录，点赞，收藏，解答等
7. 登录，音乐播放，两种 Mavon-Editor、 Vue-Quill-Editor富文本编译器，富文本文件上传和base存储

#### 使用说明

#### Ferryboatman-boot 两个服务，
##### admin前端后台
后台前端源代码 使用vue
[https://gitee.com/chongzhe/Ferryboatman-admin](http://)
##### web前端前台
前台前端源代码 使用nuxt
[https://gitee.com/chongzhe/Ferryman-blog](http://)



#### 软件架构

|      后端技术      |           说明            |                             官网                             |
| :------------: | :-----------------------: | :----------------------------------------------------------: |
|   Spring Boot   |          MVC框架          | [ https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot) |
|  Spring Boot Actuator   |        应用监控	         |          https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html           |
| Spring Security |      认证和授权框架       |          https://spring.io/projects/spring-security          |
|  MyBatis-Plus  |          ORM框架          |                   https://mp.baomidou.com/                   |
|   Swagger-UI   |       文档生产工具        | [ https://github.com/swagger-api/swagger-ui](https://github.com/swagger-api/swagger-ui) |
|    RabbitMQ    |         消息队列          |   [ https://www.rabbitmq.com/](https://www.rabbitmq.com/)    |
|     MySQL	      |        关系数据库	         |                      https://www.mysql.com/                  |
|     MongoDB	      |        分布式文件存储数据库	         |                      https://www.mongodb.org.cn/     |
|     Redis      |        分布式缓存         |                      https://redis.io/                       |
|     Docker     |        容器化部署         |      [ https://www.docker.com](https://www.docker.com/)      |
|     Druid      |       数据库连接池        | [ https://github.com/alibaba/druid](https://github.com/alibaba/druid) |
|     OSS     |     阿里云 - 对象储存     |         https://www.aliyun.com/product/oss/                    |
|     FastDFS     |     分布式文件系统	     |         https://sourceforge.net/projects/fastdfs/        |
|     七牛云     |     七牛云 - 对象储存     |         https://developer.qiniu.com/sdk#official-sdk         |
|      JWT       |        JWT登录支持        |                 https://github.com/jwtk/jjwt                 |
|     SLF4J      |         日志框架          |                    http://www.slf4j.org/                     |
|     Lombok     |     简化对象封装工具      | [ https://github.com/rzwitserloot/lombok](https://github.com/rzwitserloot/lombok) |
|     Nginx      |  HTTP和反向代理web服务器  |                      http://nginx.org/                       |
|     Hutool     |      Java工具包类库       |                  https://hutool.cn/docs/#/                   |
|     Zipkin     |         链路追踪          |             https://github.com/openzipkin/zipkin             |
|     AipFace     |         人脸识别          |             https://cloud.baidu.com/product/face?track=cp:nsem|pf:pc|pp:nsem-chanpin-renlianshibie-xiaoguo|pu:renlianshibie-pinpaici|ci:|kw:10027829&renqun_youhua=2850304             |
|     Mail     |         网易邮箱          |             https://mail.163.com/            |



|         前端技术          |        说明        |                             官网                             |
| :-------------------: |:----------------:| :----------------------------------------------------------: |
|        Vue.js         |       前端框架       |                      https://vuejs.org/                      |
|      Vue-router       |       路由框架       |                  https://router.vuejs.org/                   |
|         Vuex          |     全局状态管理框架     |                   https://vuex.vuejs.org/                    |
|        Nuxt.js        | 创建服务端渲染 (SSR) 应用 |                    https://zh.nuxtjs.org/                    |
|        Element        |      前端ui框架      |    [ https://element.eleme.io](https://element.eleme.io/)    |
|         Axios         |     前端HTTP框架     | [ https://github.com/axios/axios](https://github.com/axios/axios) |
|       Vue-Quill-Editor        |      富文本编辑器      |                    https://github.surmon.me/vue-quill-editor/                  |
|     Less      |    CSS 预处理语言     |        http://lesscss.cn/          |
|        Mavon-Editor         |   Markdown编辑器    |             https://github.com/hinesboy/mavonEditor            |
|        Aplayer        |      音乐播放器       |             https://aplayer.js.org/#/            |
|        Vue-i18n        |       国际化        |             https://kazupon.github.io/vue-i18n/zh/introduction.html           |
|        Cookie        |   认证cookie&&缓存   |             https://www.runoob.com/js/js-cookies.html           |
|        d2-admin           |       后台模板       |             https://github.com/d2-projects/d2-admin           |



- 系统架构图
![输入图片说明](https://images.gitee.com/uploads/images/2021/0729/175813_2165d07a_2227854.jpeg "FerryMan.jpeg")

- 系统截图
![输入图片说明](https://images.gitee.com/uploads/images/2021/0727/213440_856967bb_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/230822_9db8f56f_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/230841_fde7a87f_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/230902_5422a3de_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/230927_2e1c1b56_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/230935_411ec941_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/231253_e1fa4602_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/231811_8f1815ca_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/231318_fa3136a8_2227854.png "屏幕截图.png")
![输入图片说明](https://images.gitee.com/uploads/images/2021/0714/231830_57b5c14f_2227854.png "屏幕截图.png")

#### 提醒
后台音乐模块音乐上传需要搭建容器FDFS文件管理
1，拉取镜像并启动
 
docker run -d --restart=always --privileged=true --net=host --name=fastdfs -e IP=1.116.227.4 -e WEB_PORT=80 -v ${HOME}/fastdfs:/var/local/fdfs registry.cn-beijing.aliyuncs.com/tianzuo/fastdfs
​
其中-v ${HOME}/fastdfs:/var/local/fdfs是指：将${HOME}/fastdfs这个目录挂载到容器里的/var/local/fdfs这个目录里。所以上传的文件将被持久化到${HOME}/fastdfs/storage/data里，IP 后面是自己的服务器公网ip或者虚拟机ip，-e WEB_PORT=80 指定nginx端口
 
 
2，测试上传
 
//进入容器
docker exec -it fastdfs /bin/bash
//创建文件
echo "FastDFS!">index.html
//测试文件上传
fdfs_test /etc/fdfs/client.conf upload index.html

备注：
需要nignx

配置sbin目录的nginx.config
加入 user root;
修改
	listen       80;
        server_name  ip;

	        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location ~/group1/M00 {
            root  /var/local/fdfs;  #${HOME}/fastdfs:/var/local/fdfs 
            ngx_fastdfs_module;
        }
