package com.javasm.bean.returnData;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReturnData<T> {
    private String msg;
    //private String token;
    private Integer code;
    private T t;
}
