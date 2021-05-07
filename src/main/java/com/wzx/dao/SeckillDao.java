package com.wzx.dao;

import com.wzx.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * @author wangzx22@163.com
 * @date 2021/5/7 下午8:12
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(long seckillId, Date killTime);

    /**
     * 查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 查询指定范围的所有秒杀单
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offset, int limit);
}
