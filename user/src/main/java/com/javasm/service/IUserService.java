package com.javasm.service;

import com.javasm.bean.User;
import com.javasm.bean.returnData.ReturnData;


/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2023/4/12-11:50
 * @Since：jdk1.8
 * @Description：
 */
public interface IUserService {
    ReturnData login(User user);

    ReturnData findAll();
}
