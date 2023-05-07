package com.javasm.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_seckill_order")
public class SecOrder {
    private Integer id;
    private Integer userId;
    private Integer order_id;
    private Integer goodsId;
}
