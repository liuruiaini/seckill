package com.javasm.filter;


import com.alibaba.fastjson.JSONObject;
import com.javasm.bean.LoginUser;
import com.javasm.bean.User;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2023/4/12-17:13
 * @Since：jdk1.8
 * @Description：
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 请求来这里，需要给我提供token

        if (httpServletRequest.getRequestURI().contains("/user/login")){
           // System.out.println(httpServletRequest.getHeaderNames());
            System.out.println(httpServletRequest.getRequestURI());
            //System.out.println(httpServletRequest.getHeader("token"));
            //System.out.println(getPostData(httpServletRequest));
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
// 获取token
        String token = httpServletRequest.getHeader("token");
        if (!StringUtils.hasText(token)) {
            httpServletResponse.getWriter().write("{\"success\":false,\"msg\":\"NO LOGIN！\"}");
            return;
        }
        // 有token ,校验token
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String s = ops.get(token);
        //ReturnData returnData = (ReturnData)JSON.parse(s);
        User user = JSONObject.parseObject(s, User.class);
        System.out.println(user);
        if (Objects.nonNull(user)){
            LoginUser loginUser = new LoginUser(user);
            Authentication auth =
                    new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
            // 说明 token对的。放行，将认证通过信息存到security中。
            SecurityContextHolder.getContext().setAuthentication(auth);
            // 放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }

    private static String getPostData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine()))
                data.append(line);
        } catch (IOException e) {
        } finally {
        }
        return data.toString();
    }

}
