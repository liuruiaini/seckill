package com.javasm.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.javasm.bean.Order;
import com.javasm.bean.SecGoods;
import com.javasm.bean.User;
import com.javasm.bean.UserAndSecGoodsId;
import com.javasm.mapper.OrderMapper;
import com.javasm.mapper.SecGoodsMapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RocketMQMessageListener(topic = "secKill",consumerGroup = "user")
public class Consumer implements RocketMQListener<UserAndSecGoodsId> {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SecGoodsMapper secGoodsMapper;
    @Override
    public void onMessage(UserAndSecGoodsId userAndSecGoodsId) {
        Integer id=userAndSecGoodsId.getId();
        User u=userAndSecGoodsId.getUser();
        //生成订单
        Order order=new Order();
        order.setUserId(u.getUid());
        order.setGoodsId(id);
        order.setDeliveryAddrId(1);
        //order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        //order.setGoodsPrice(goods.getGoodsPrice());
        order.setOrderChannel(1);
        order.setStatus(1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        order.setCreateDate(format);
        int insert = orderMapper.insert(order);
        stringRedisTemplate.opsForValue().set("order:"+u.getUid()+":"+id, JSON.toJSONString(order));
        UpdateWrapper<SecGoods> wrapper = new UpdateWrapper<SecGoods>().setSql("stock_count=" + "stock_count-1").eq("goods_id", id).gt("stock_count", 0);
        int update = secGoodsMapper.update(null, wrapper);
    }
}
