package com.wzx.dao;

import com.wzx.entity.SuccessKilled;

import java.util.Date;

/**
 * @author wangzx22@163.com
 * @date 2021/5/7 下午8:14
 */
public interface SuccessKilledDao {

    /**
     * 生成秒杀记录
     * @param seckillId
     * @param uesrPhone
     * @param createTime
     * @return
     */
    int insertSuccessKilled(long seckillId, long uesrPhone, Date createTime);

    /**
     * 查询指定商品id的所有秒杀记录, 并携带秒杀单
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(long seckillId);
}
