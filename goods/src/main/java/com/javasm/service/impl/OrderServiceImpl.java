package com.javasm.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.javasm.bean.*;
import com.javasm.exception.BusinessEnum;
import com.javasm.exception.MyRuntimeException;
import com.javasm.mapper.GoodsMapper;
import com.javasm.mapper.OrderMapper;
import com.javasm.mapper.SecGoodsMapper;
import com.javasm.mapper.SecOrderMapper;
import com.javasm.service.OrderService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private SecOrderMapper secOrderMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SecGoodsMapper secGoodsMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public SecOrder selectone(LambdaQueryWrapper<SecOrder> eq) {
        return secOrderMapper.selectOne(eq);
    }

    @Override
    public Order selectoneOrder(LambdaQueryWrapper<Order> eq) {
        return orderMapper.selectOne(eq);
    }

    @Override
    @Transactional
    public  Order doSecKill(User u,Integer id) {
        //redis预减库存
        Long decrement = stringRedisTemplate.opsForValue().decrement("secGoods:" + id);
        if(decrement<0){
            stringRedisTemplate.opsForValue().increment("secGoods:" + id);
            return null;
        }
        //想mq发送消息
        rocketMQTemplate.asyncSend("secKill" , new UserAndSecGoodsId().setUser(u).setId(id), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                stringRedisTemplate.opsForValue().increment("secGoods:" + id);
            }
        });

        return null;
    }
}
