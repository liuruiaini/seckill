package com.javasm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo1 {
    @RequestMapping("demo1")
    public String demo1(){
        return "demo1";
    }
}
