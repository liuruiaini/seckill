package com.javasm.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_seckill_goods")
public class SecGoods {
    private Integer id;
    private Integer goodsId;
    private Double seckillPrice;
    private Integer stockCount;
    private String startDate;
    private String endDate;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String goodsImg;
    @TableField(exist = false)
    private Double goodsPrice;
}
