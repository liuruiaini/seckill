package com.javasm.service;

import com.javasm.bean.Order;
import com.javasm.bean.SecGoods;
import com.javasm.bean.SecOrder;
import com.javasm.bean.returnData.ReturnData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GoodService {
    ReturnData<List<SecGoods>> initTable();

    ReturnData<SecGoods> findInitGoodsById(Integer id);

    ReturnData<Order> secKill(Integer id, HttpServletRequest request);
}
