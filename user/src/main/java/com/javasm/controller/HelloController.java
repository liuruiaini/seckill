package com.javasm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2023/4/12-10:56
 * @Since：jdk1.8
 * @Description：
 */
@RestController
@RequestMapping("hello")
public class HelloController {
    @RequestMapping("hello")
    public String hello(){
        return "success";
    }
    @RequestMapping("login")
    public String login(){
        return "success";
    }
}
