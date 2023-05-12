package com.javasm.service.impl;


import com.javasm.bean.LoginUser;
import com.javasm.bean.User;
import com.javasm.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2023/4/12-16:27
 * @Since：jdk1.8
 * @Description：
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = mapper.loadUserByUsername(s);
        LoginUser loginUser = new LoginUser(u);
        System.out.println(loginUser.getAuthorities());
        return loginUser;
    }
}
