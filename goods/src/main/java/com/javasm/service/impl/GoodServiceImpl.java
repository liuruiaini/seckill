package com.javasm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.bean.*;
import com.javasm.bean.returnData.ReturnData;
import com.javasm.exception.BusinessEnum;
import com.javasm.exception.MyRuntimeException;
import com.javasm.mapper.GoodsMapper;
import com.javasm.mapper.SecGoodsMapper;
import com.javasm.service.GetUser;
import com.javasm.service.GoodService;
import com.javasm.service.OrderService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {
    private String token;
    @Resource
    private SecGoodsMapper secGoodsMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private GetUser getUser;
    @Resource
    private OrderService orderService;
    @Override
    public ReturnData<List<SecGoods>> initTable() {
        List<SecGoods> secGoodsList = secGoodsMapper.selectList(null);
        for (SecGoods secGoods : secGoodsList) {
            Goods goods = goodsMapper.selectById(secGoods.getGoodsId());
            secGoods.setGoodsImg(goods.getGoodsImg());
            secGoods.setGoodsName(goods.getGoodsName());
            secGoods.setGoodsPrice(goods.getGoodsPrice());
        }
        return new ReturnData().setMsg("代码正确").setCode(200).setT(secGoodsList);
    }

    @Override
    public ReturnData<SecGoods> findInitGoodsById(Integer id) {
        SecGoods secGoods = secGoodsMapper.selectById(id);
        Goods goods = goodsMapper.selectById(secGoods.getGoodsId());
        secGoods.setGoodsName(goods.getGoodsName());
        secGoods.setGoodsPrice(goods.getGoodsPrice());
        secGoods.setGoodsImg(goods.getGoodsImg());
        secGoods.setEndDate(secGoods.getEndDate().substring(0,secGoods.getEndDate().length()-2));
        secGoods.setStartDate(secGoods.getStartDate().substring(0,secGoods.getStartDate().length()-2));
        return new ReturnData().setT(secGoods).setMsg("代码正确").setCode(200);
    }

    @Override

    public ReturnData<Order> secKill(Integer id, HttpServletRequest request) {
        //1.用户未登录
        String token1 = request.getHeader("token");
        this.setToken(token1);
        System.out.println(token1);
        String login = getUser.isLogin();
        System.out.println(login);
        if(!login.equals("11111111111111")){//抛出这个异常，直接给前段返回异常代码了
            throw new MyRuntimeException(BusinessEnum.USER_NOLOGIN_EXCEPTION);
        }
        //1.5通过request查询当前登录用户
        User u=(User) getUser.info().getT();
        System.out.println(u);
        if(null==u){
            throw new MyRuntimeException(BusinessEnum.USER_NOLOGIN_EXCEPTION);
        }
        //2.库存不足
        SecGoods secGoods = secGoodsMapper.selectById(id);
        if(secGoods.getStockCount()<=0){
            throw new MyRuntimeException(BusinessEnum.SECGOOD_COUNT_NOTENOUGH_EXCEPTION);
        }
        //3.只能支付一次
        SecOrder secOrder=orderService.selectone(new LambdaQueryWrapper<SecOrder>().eq(SecOrder::getUserId,u.getUid()).eq(SecOrder::getGoodsId,id));
        if(null!=secOrder){
         throw new MyRuntimeException(BusinessEnum.REPEAT_SECORDER_EXCEPTION);
        }
        //商品库存和秒杀商品库存减一
        secGoods.setStockCount(secGoods.getStockCount()-1);
        int i = secGoodsMapper.updateById(secGoods);
        Goods goods = goodsMapper.selectById(id);
        goods.setGoodsStock(goods.getGoodsStock()-1);
        int i1 = goodsMapper.updateById(goods);

        //生成订单和秒杀商品订单
        Order order=orderService.insertOrder(u.getUid(),goods);
        return new ReturnData<Order>().setCode(200).setMsg("订单生成成功").setT(order);
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token){
        this.token=token;
    }
}
