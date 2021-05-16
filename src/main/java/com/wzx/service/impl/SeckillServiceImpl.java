package com.wzx.service.impl;

import com.wzx.dao.SeckillDao;
import com.wzx.dao.SuccessKilledDao;
import com.wzx.dto.Exposer;
import com.wzx.dto.SeckillExecution;
import com.wzx.entity.Seckill;
import com.wzx.entity.SuccessKilled;
import com.wzx.enums.SeckillExecutionStateEnum;
import com.wzx.exception.ClosedSeckillException;
import com.wzx.exception.RepeatedSeckillException;
import com.wzx.exception.SeckillException;
import com.wzx.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 秒杀业务实现
 *
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/10 下午10:07
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 混淆值
    private final String salt = "fjdKJk:JFdoijf*((*@#$#dfj;k/Dfji21!@#&";

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    public List<Seckill> getAllSeckill() {
        int count = seckillDao.count();
        return seckillDao.queryAll(0, count);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        // 拿到秒杀对象, 判断秒杀是否开启
        Seckill seckill = seckillDao.queryById(seckillId);
        // 没有指定的秒杀业务
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date now = new Date();
        // 不在秒杀时间范围内
        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 使用注解控制事务的优点:
     * 1. 统一风格, 开发团队达成一致约定, 明确标注事务方法的编程风格
     * 2. 保证事务方法的执行时间尽可能短, 不要穿插其他RPC/HTTP等网络请求, 可剥离到事务方法外
     * 3. 不是所有的方法都需要事务
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatedSeckillException
     * @throws ClosedSeckillException
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.NESTED)
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatedSeckillException, ClosedSeckillException {
        try {
            // 判断md5值
            if (md5 == null || !md5.equals(getMD5(seckillId))) {
                throw new SeckillException("scekill data rewrite");
            }
            // 减库存
            Date now = new Date();
            int updateCount = seckillDao.reduceNumber(seckillId, now);
            // 减库存失败, 可能是时间无效, 库存没了, 都是秒杀结束
            if (updateCount <= 0) {
                throw new ClosedSeckillException("seckill is closed");
            }
            // 减库存成功, 记录购买明细
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            // 插入失败, 重复秒杀
            if (insertCount <= 0) {
                throw new RepeatedSeckillException("seckill repeated");
            }
            SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
            return new SeckillExecution(seckillId, SeckillExecutionStateEnum.SUCCESS, successKilled);
        } catch (ClosedSeckillException e2) {
            throw e2;
        } catch (RepeatedSeckillException e3) {
            throw e3;
        } catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
            throw new SeckillException("seckill inner error: " + e1.getMessage());
        }
    }

    /**
     * 增加混淆后, 返回md5值
     *
     * @param seckillId
     * @return
     */
    private String getMD5(long seckillId) {
        String base = salt + "/" + seckillId;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
