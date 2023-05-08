# seckill
集群版
## 一.简述
考虑是否数据库性能影响了整个商品详情接口性能，使用一主一从和一主两从数据库集群测试。
项目简述：使用mysql集群测试商品详情页面并发，user模块使用springSecurity实现认证和授权；getway做网关层，nginx做负载均衡和反向代理；
其中微服务和nginx部署在window环境下；redis集群和mysql集群部署在linux环境下
## 二.前端页面
### 登录接口
![image](https://user-images.githubusercontent.com/38555600/236695880-ec8869df-9698-4fe0-b102-1de458def38d.png)
### 商品列表接口
![image](https://user-images.githubusercontent.com/38555600/236695948-8e01f2a1-039e-4d19-886b-fb02bb95408b.png)
### 商品详情接口和秒杀接口
![image](https://user-images.githubusercontent.com/38555600/236695988-f2d0f898-d5cb-4fd2-b7ae-c11f95982c95.png)
### 秒杀成功直接订单数据给前端
## 三.jMeter测试数据
### 创建系统最大吞吐量接口(不经过任何处理，直接返回字符串儿"success")和最大吞吐量，测试为5800

![image](https://user-images.githubusercontent.com/38555600/236696125-245123b6-6cd6-45e6-a282-e25ac4c49b47.png)


![空接口](https://user-images.githubusercontent.com/38555600/236696222-a1995359-2b3e-4e46-b18a-251d5418f9cf.png)
### 商品详情页接口吞吐量测试——mysql集群版——一主一从(Percona Serve搭建主从，mycat实现负载均衡)，测试为509
![商品页面接口](https://user-images.githubusercontent.com/38555600/236696430-8eb7404d-2f42-4ce4-8b34-6f7379e76f99.png)
### 商品详情页接口吞吐量测试——mysql集群版——一一主两从(Percona Serve搭建主从，mycat实现负载均衡)，测试为348

![商品列表](https://user-images.githubusercontent.com/38555600/236771645-1d637b71-12a1-4bba-9983-d94f5074c75e.png)
## 测试结果
商品列表页接口:单个mysql服务吞吐量达到1500,配置一主一从吞吐量500，一主两从吞吐量350；说明不是mysql影响系统吞吐量，应从其他方面考虑；
