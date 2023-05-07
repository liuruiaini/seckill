package com.javasm.controller;

import com.alibaba.fastjson.JSONObject;
import com.javasm.bean.User;
import com.javasm.bean.returnData.ReturnData;
import com.javasm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2023/4/12-11:47
 * @Since：jdk1.8
 * @Description：
 */
@RestController
//@CrossOrigin
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService service;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("login")
    public ReturnData login(@RequestBody User user){
        return  service.login(user);
    }
//    @RequestMapping("login")
////    @CrossOrigin
//    public ReturnData login(String userName,String password ){
//        return  service.login(new User().setUserName(userName).setPassword(password));
//    }
    @RequestMapping ("findAll")
    public ReturnData findAll(){

        return  service.findAll();
    }
    @RequestMapping ("isLogin")
    public String isLogin(){
        return  "11111111111111";
    }
    @RequestMapping ("info")
    public ReturnData<User> info(HttpServletRequest req){
        String token = req.getHeader("token");
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String s = ops.get(token);
       //ReturnData returnData = (ReturnData)JSON.parse(s);
        User redisUser = JSONObject.parseObject(s, User.class);
        if(redisUser!=null){
           return new ReturnData().setT(redisUser).setCode(200).setMsg("查询成功");
       }

       return null;
    }
    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("add")
    public String add(){
        return "add";
    }
}
