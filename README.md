# seckill
## 秒杀接口优化

## 1.使用redis预减库存和rockmq异步下单（）

1.controller中加上InitializingBean接口中的 afterPropertiesSet()方法，使得程序启动时redis中就可以获取库存数据（更规范的做法是当创建秒杀商品表的时候预存库存）；key：表名：商品id，value：库存数；当请求过来没有被登录异常和重复抢购异常拦截，就直接可以遇见库存了，如果请求在这里积压也没关系，因为redis是单线程的；但如果是分布式请求，则要用redis分布式锁；

```java
@RestController
@RequestMapping("goods")
public class GoodController implements InitializingBean {
    @Resource
    private GoodService goodService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("isLogin")
    public ReturnData isLogin(HttpServletRequest request){

           //调用user模块是否登录
        return new ReturnData();
   }
    @RequestMapping("initTable")
    //@Async
    public ReturnData<List<SecGoods>> initTable(){
        return goodService.initTable();
    }
    @RequestMapping("test")
    //@Async
    public String test(){
        return "success";
    }
    @RequestMapping("findInitGoodsById")
    public ReturnData<SecGoods> findInitGoodsById(Integer id){
        return goodService.findInitGoodsById(id);
    }
    @RequestMapping("secKill")
    public ReturnData<Order> secKill(Integer id, HttpServletRequest request){
        return goodService.secKill(id,request);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SecGoods> secGoodsList = goodService.initTable().getT();
        if(null==secGoodsList){
            return;
        }
        secGoodsList.forEach(secGoods -> {
            stringRedisTemplate.opsForValue().set("secGoods:"+secGoods.getGoodsId(),secGoods.getStockCount().toString());
        });
    }
}
```

```
 //redis预减库存
        Long decrement = stringRedisTemplate.opsForValue().decrement("secGoods:" + id);
        if(decrement<0){
            stringRedisTemplate.opsForValue().increment("secGoods:" + id);
            return null;
        }
```



### 系统最大吞吐量

![1683710548437](C:/Users/12166/AppData/Roaming/Typora/typora-user-images/1683710548437.png)

### 高并发场景下订单数据和库存数据正常

![](D:/秒杀项目图片记录/秒杀接口/redis/数据库.png)

![预减成功](D:/秒杀项目图片记录/秒杀接口/redis/预减成功.png)

### 接口吞吐量从700多提升到1800；减少了一次数据库查询和一次数据库修改

![1683722696875](C:/Users/12166/AppData/Roaming/Typora/typora-user-images/1683722696875.png)

## 2.使用RockMQ异步下单

将user和商品id封装成对象，使用异步发送的方式放入rocketMq，异步不存在发消息重复但会存在消息丢失；对于消息丢失，可以在回调函数中处理，onseccess()方法中不处理。失败的回调方法中是库存+1，相当于多一次请求消费的机会；

同步消息，发送消息的时候宕机了，由于重试机制可能会导致消息重复：

处理方法（也就是如何保证接口幂等性）：1.使用联合唯一索引，插入数据时会被索引拦截2.加一个版本号，对于修改操作只能修改固定版本号，每次修改完版本号+1

![](D:/秒杀项目图片记录/秒杀接口/redis+rocketMq/redis预减库存.png)

![订单](D:/秒杀项目图片记录/秒杀接口/redis+rocketMq/订单.png)

![库存](D:/秒杀项目图片记录/秒杀接口/redis+rocketMq/库存.png)

![吞吐量1200](D:/秒杀项目图片记录/秒杀接口/redis+rocketMq/吞吐量1200.png)

秒杀接口吞吐量1200，其实也还好



