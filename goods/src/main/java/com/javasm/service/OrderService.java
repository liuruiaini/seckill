package com.javasm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.bean.*;

public interface OrderService {
    SecOrder selectone(LambdaQueryWrapper<SecOrder> eq);
    Order selectoneOrder(LambdaQueryWrapper<Order> eq);

    Order doSecKill(User u,Integer id);
}
