package com.wzx.exception;

/**
 * 重复秒杀异常
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/10 下午9:58
 */
public class RepeatedSeckillException extends SeckillException {
    public RepeatedSeckillException(String message) {
        super(message);
    }

    public RepeatedSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
