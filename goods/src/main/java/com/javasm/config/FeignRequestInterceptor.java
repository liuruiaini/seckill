package com.javasm.config;

import com.javasm.service.GoodService;
import com.javasm.service.impl.GoodServiceImpl;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {//feign拦截器，会拦截所有feign请求
    @Resource
    private GoodServiceImpl goodService;
    @Override
    public void apply(RequestTemplate requestTemplate) {

         requestTemplate.header("token",goodService.getToken());
    }
}
