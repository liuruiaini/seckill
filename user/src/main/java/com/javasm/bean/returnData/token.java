package com.javasm.bean.returnData;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_token")
public class token {
    private Long id;
    private String token;
}
