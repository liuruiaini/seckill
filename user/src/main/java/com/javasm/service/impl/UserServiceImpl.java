package com.javasm.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javasm.bean.LoginUser;
import com.javasm.bean.User;
import com.javasm.bean.returnData.ReturnData;
import com.javasm.bean.returnData.token;
import com.javasm.exception.BusinessEnum;
import com.javasm.exception.MyRuntimeException;
import com.javasm.mapper.TokenMapper;
import com.javasm.mapper.UserMapper;
import com.javasm.mapper.UserMapperPlus;
import com.javasm.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2023/4/12-11:51
 * @Since：jdk1.8
 * @Description：
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper mapper;
    @Resource
    private UserMapperPlus mapperPlus;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AuthenticationManager manager;
    @Resource
    private TokenMapper tokenMapper;
    @Override
    public ReturnData login(User user) {
//        User u = mapper.loadUserByUsername(user.getUserName());
//        if (Objects.isNull(u)){
//            //没查到，用户名不存在
//            return new ReturnData().setT(500).setMsg("登录失败，账号或密码错误");
//        }
//        //查到了，比对密码
//        if (user.getPassword().equals(u.getPassword())){
//            // 登录
//            //将当前用户token存入redis
//            ReturnData returnData=new ReturnData();
//            String token=UUID.randomUUID().toString();
//            returnData.setCoed(200).setMsg("登录成功！！").setT(u).setToken(token);
//            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//            ops.set(token,JSON.toJSONString(returnData));
//
//
//            return returnData;
//        }
//        return new ReturnData().setT(500).setMsg("登录失败，账号或密码错误");
        Authentication token =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 返回完整的Authentication对象，里面放了详细信息。
        Authentication auth = manager.authenticate(token);
        if(Objects.isNull(auth)){
            throw new MyRuntimeException(BusinessEnum.USERNAME_PASSWORD_ERROR_EXCEPTION);
        }
        // 目的：想要给前端返回用户数据，如何找到这个数据呢？面向对象思想。
        //System.out.println(auth);
        ReturnData returnData=new ReturnData();
        LoginUser principal = (LoginUser) auth.getPrincipal();
        //查看principal中的权限集合
        for (GrantedAuthority authority : principal.getAuthorities()) {
            System.out.println(authority.getAuthority());
        }
        User redisUser=principal.getUser();
        
        String uuid=UUID.randomUUID().toString();
        token token1 = new token();
        token1.setToken(uuid);
        token1.setId(redisUser.getUid());
        tokenMapper.insert(token1);
        returnData.setCode(200).setMsg("登录成功!!!").setT(principal).setToken(uuid);
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(uuid,JSON.toJSONString(redisUser));
        System.out.println(JSON.toJSONString(redisUser));
        return returnData;
   }

    @Override
    public ReturnData findAll() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        List<User> users = mapperPlus.selectList(null);
        if (users!=null&&users.size()>0){
            return new ReturnData().setCode(200).setMsg("找到了").setT(users);
        }
        return new ReturnData().setT(500).setMsg("没找到");

    }
}
