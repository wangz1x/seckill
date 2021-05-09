package com.wzx.dao;

import com.wzx.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * @author wangzx22@163.com
 * @date 2021/5/7 下午8:14
 */
public interface SuccessKilledDao {

    /**
     * 生成秒杀记录
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 查询指定用户对某商品的秒杀记录, 并携带秒杀单
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone")  long userPhone);
}
