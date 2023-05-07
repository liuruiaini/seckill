package com.javasm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.bean.Goods;
import com.javasm.bean.Order;
import com.javasm.bean.SecOrder;
import com.javasm.mapper.OrderMapper;
import com.javasm.mapper.SecOrderMapper;
import com.javasm.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private SecOrderMapper secOrderMapper;
    @Resource
    private OrderMapper orderMapper;

    @Override
    public SecOrder selectone(LambdaQueryWrapper<SecOrder> eq) {
        return secOrderMapper.selectOne(eq);
    }

    @Override
    public Order insertOrder(Long uid, Goods goods) {
        Order order=new Order();
        order.setUserId(uid);
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(1);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(goods.getGoodsPrice());
        order.setOrderChannel(1);
        order.setStatus(1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        order.setCreateDate(format);
        int insert = orderMapper.insert(order);

        //Order order1 = orderMapper.selectById(insert);
        return order;
    }
}
