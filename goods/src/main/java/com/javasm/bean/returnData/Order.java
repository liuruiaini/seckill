package com.javasm.bean.returnData;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class Order {
    private Integer id;
    private Integer userId;
    private Integer goodsId;
    private Integer deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private String createDate;
    private String payDate;
}
