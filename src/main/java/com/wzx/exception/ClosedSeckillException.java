package com.wzx.exception;

/**
 * 秒杀关闭异常
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/10 下午9:58
 */
public class ClosedSeckillException extends SeckillException{
    public ClosedSeckillException(String message) {
        super(message);
    }

    public ClosedSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
