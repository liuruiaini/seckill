package com.javasm.service;

import com.javasm.bean.User;
import com.javasm.bean.returnData.ReturnData;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Component
@FeignClient(name = "user")
public interface GetUser {
    @GetMapping (value = "/user/isLogin")
    String isLogin();
    @RequestMapping(value = "/user/info")
    ReturnData<User> info();
}
