# seckill
## 商品列表页接口性能提升
### 系统最大吞吐量
### 商品列表页吞吐量

![商品列表接口](https://user-images.githubusercontent.com/38555600/236776141-db8aee44-ff8f-4040-b192-c85e92b615ec.png)
### 优化思路
1.tomcat服务器性能：采用tomcat9，NIO服务模型，服务器性能已是最优
2.数据库方向——通过搭建mysql集群，但性能从1500下降至350，详情看分支测试结果
3.添加缓存
