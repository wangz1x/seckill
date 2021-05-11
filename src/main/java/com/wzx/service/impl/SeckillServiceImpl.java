package com.wzx.service.impl;

import com.wzx.dao.SeckillDao;
import com.wzx.dao.SuccessKilledDao;
import com.wzx.dto.Exposer;
import com.wzx.dto.SeckillExecution;
import com.wzx.entity.Seckill;
import com.wzx.exception.ClosedSeckillException;
import com.wzx.exception.RepeatedSeckillException;
import com.wzx.exception.SeckillException;
import com.wzx.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 秒杀业务实现
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/10 下午10:07
 */

public class SeckillServiceImpl implements SeckillService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private SeckillDao seckillDao;

    private SuccessKilledDao successKilledDao;

    public List<Seckill> getAllSeckill() {
        return seckillDao.queryAll(0, -1);
    }

    public Seckill getById(long seckillId) {
        return null;
    }

    public Exposer exportSeckillUrl(long seckillId) {
        return null;
    }

    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatedSeckillException, ClosedSeckillException {
        return null;
    }
}
