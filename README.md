# seckill
## 秒杀口初始版本实现思路以及存在的问题
### 思路
1.从前端拿到用户token和秒杀商品id <br/>
2.调用user服务接口的islogin()方法和info()方法判断用户是否登录和拿到用户id <br/>
3.库存不足判断 <br/>
4.重复消费判断 <br/>
5.库存减一 <br/>
6.生成订单 <br/>
````
public ReturnData<Order> secKill(Integer id, HttpServletRequest request) {
        //1.用户未登录
        String token1 = request.getHeader("token");
        this.setToken(token1);
        //System.out.println(token1);
        String login = getUser.isLogin();
        //System.out.println(login);
        if(!login.equals("11111111111111")){//抛出这个异常，直接给前段返回异常代码了
            throw new MyRuntimeException(BusinessEnum.USER_NOLOGIN_EXCEPTION);
        }
        //1.5通过request查询当前登录用户
        User u=(User) getUser.info().getT();
        //System.out.println(u);
        if(null==u){
            throw new MyRuntimeException(BusinessEnum.USER_NOLOGIN_EXCEPTION);
        }
        //2.库存不足
        SecGoods secGoods = secGoodsMapper.selectById(id);
        if(secGoods.getStockCount()<=0){
            throw new MyRuntimeException(BusinessEnum.SECGOOD_COUNT_NOTENOUGH_EXCEPTION);
        }
        //3.重复消费判断
        Order order=orderService.selectoneOrder(new LambdaQueryWrapper<Order>().eq(Order::getUserId,u.getUid()).eq(Order::getGoodsId,id));
        if(null!=order){
         throw new MyRuntimeException(BusinessEnum.REPEAT_SECORDER_EXCEPTION);
        }
        //商品库存和秒杀商品库存减一
        secGoods.setStockCount(secGoods.getStockCount()-1);
        System.out.println("秒杀商品库存"+secGoods.getStockCount());
        int i = secGoodsMapper.updateById(secGoods);
        Goods goods = goodsMapper.selectById(id);
        goods.setGoodsStock(goods.getGoodsStock()-1);
        System.out.println("商品库存"+goods.getGoodsStock());
        int i1 = goodsMapper.updateById(goods);

        //生成订单和秒杀商品订单
        Order order1=orderService.insertOrder(u.getUid(),goods);
        return new ReturnData<Order>().setCode(200).setMsg("订单生成成功").setT(order1);
    }
````


### 存在问题及原因：
1.多个用户抢到同一件商品：执行减库存操作时多个线程查询当前库存数  <br/>
![不同用户抢到同一件商品](https://user-images.githubusercontent.com/38555600/236921397-bff29082-ea4a-428c-ae70-1f458527beae.png)  <br/>
2.商品超卖：库存为负数  <br/>
![库存超卖](https://user-images.githubusercontent.com/38555600/236921589-0f77ad94-dcf5-4570-8175-8b971424abb1.png)  <br/>

### 优化思路
