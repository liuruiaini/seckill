package com.javasm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.bean.Goods;
import com.javasm.bean.Order;
import com.javasm.bean.SecOrder;

public interface OrderService {
    SecOrder selectone(LambdaQueryWrapper<SecOrder> eq);

    Order insertOrder(Long uid, Goods goods);
}
