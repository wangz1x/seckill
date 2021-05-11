package com.wzx.exception;

/**
 * 秒杀业务异常
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/10 下午9:57
 */
public class SeckillException extends RuntimeException{
    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
