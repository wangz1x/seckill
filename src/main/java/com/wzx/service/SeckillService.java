package com.wzx.service;

import com.wzx.dto.Exposer;
import com.wzx.dto.SeckillExecution;
import com.wzx.entity.Seckill;
import com.wzx.exception.ClosedSeckillException;
import com.wzx.exception.RepeatedSeckillException;
import com.wzx.exception.SeckillException;

import java.util.List;

/**
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/10 下午9:26
 */
public interface SeckillService {

    /**
     * 获取所有秒杀业务
     * @return
     */
    List<Seckill> getAllSeckill();

    /**
     * 获取指定秒杀业务
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 暴露秒杀接口
     * 1. 秒杀已开启则输出秒杀地址
     * 2. 秒杀未开启返回相关时间用于显示
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀的结果
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatedSeckillException
     * @throws ClosedSeckillException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatedSeckillException, ClosedSeckillException;
}
