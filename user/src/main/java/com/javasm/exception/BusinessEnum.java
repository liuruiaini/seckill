package com.javasm.exception;




public enum BusinessEnum {
    PRIMETER_EXCEPTION(101,"参数异常"),
    INTERFACEOUTTIME_EXCEPTION(102,"接口调用超时异常"),
    AOP_EXCEPTION(103,"刚刚那个AOP异常了！！！"),
    EMAIL_EXCEPTION(104,"邮件发送失败"),
    SQL_DEL_EXCEPTION(105,"删除数据异常"),
    SQL_ADD_EXCEPTION(105,"添加数据异常"),
    SQL_UPDATE_EXCEPTION(105,"更新数据异常"),
    SQL_FIND_EXCEPTION(105,"查询数据异常"),
    //秒杀业务
    USER_NOLOGIN_EXCEPTION(106,"未登录"),
    USERNAME_PASSWORD_ERROR_EXCEPTION(109,"用户名或密码错误"),
    SECGOOD_COUNT_NOTENOUGH_EXCEPTION(600,"库存不足了兄弟"),
    REPEAT_SECORDER_EXCEPTION(601,"每个人只能抢一次兄弟");

    private BusinessEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
