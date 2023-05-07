package com.javasm.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.javasm.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User {
    private Long uid;
    @TableField(value = "userName")
    private String userName;
    private String phone;
    @JsonProperty(value = "userPwd")
    private String password;
    @TableField(value = "nickName")
    private String nickName;
    private String role;

    private String permision;
    @TableField(value = "isBlacklist")
    private Integer isBlacklist;
    public List<String> getPermisionList(){
       return StringUtil.StringToList(permision);
    }


}