package com.javasm.controller;

import com.javasm.bean.Order;
import com.javasm.bean.SecGoods;
import com.javasm.bean.SecOrder;
import com.javasm.bean.returnData.ReturnData;
import com.javasm.service.GoodService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
