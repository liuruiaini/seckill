# seckill
## 商品列表页接口性能提升
### 系统最大吞吐量
![image](https://user-images.githubusercontent.com/38555600/236776946-85245c44-9b69-4dca-b16c-4a3fd1942dec.png)

### 商品列表页吞吐量（redis优化前）

![商品列表接口](https://user-images.githubusercontent.com/38555600/236776141-db8aee44-ff8f-4040-b192-c85e92b615ec.png)
### 商品列表页吞吐量（redis优化后）
![3700](https://user-images.githubusercontent.com/38555600/236807455-008484ae-ad37-4320-96e6-16a1611f09bd.png)

### 优化思路
1.添加缓存
### 结果
商品列表页吞量由1500提升至3800
