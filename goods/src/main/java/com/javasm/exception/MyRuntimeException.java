package com.javasm.exception;

import lombok.Data;

@Data
public class MyRuntimeException extends RuntimeException {
    private  BusinessEnum businessEnum;
    public MyRuntimeException(BusinessEnum businessEnum){
        this.businessEnum=businessEnum;
    }
}
