package com.javasm.exception;


import com.javasm.bean.returnData.ReturnData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {//全局异常处理类，拦截所有异常
    private final static Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //处理参数异常
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ReturnData handleMissingServletRequestParameterException(MissingServletRequestParameterException ex){

        log.error("缺少必要请求参数,{}",ex.getMessage());
        return new ReturnData().setMsg("缺少参数兄弟").setCode(555);
    }
    @ExceptionHandler(NullPointerException.class)
    public ReturnData handNullPointerException(NullPointerException ex){
        log.error("空指针异常,{}",ex.getMessage());
        return new ReturnData().setMsg("空指针异常兄弟").setCode(666);
    }
    @ExceptionHandler(MyRuntimeException.class)
    public ReturnData handleMyRuntimeException(MyRuntimeException ex){
        log.error("异常,{}",ex.getMessage());
        return new ReturnData().setMsg(ex.getBusinessEnum().getMessage()).setCode(ex.getBusinessEnum().getCode());
    }
    @ExceptionHandler(Exception.class)
    public ReturnData handleException(Exception ex){
        log.error("异常,{}",ex.getMessage());
        return new ReturnData().setMsg("大异常！！！").setCode(777);
    }

}
