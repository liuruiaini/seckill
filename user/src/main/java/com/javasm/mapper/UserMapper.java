package com.javasm.mapper;

/**
 * @Author：liulei
 * @Version：1.0
 * @Date：2023/4/12-14:33
 * @Since：jdk1.8
 * @Description：
 */


import com.javasm.bean.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select * from t_user where userName=#{userName}")
    User loadUserByUsername(String userName);
}
