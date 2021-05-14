package com.wzx.service.impl;

import com.wzx.dto.Exposer;
import com.wzx.dto.SeckillExecution;
import com.wzx.entity.Seckill;
import com.wzx.exception.ClosedSeckillException;
import com.wzx.exception.RepeatedSeckillException;
import com.wzx.exception.SeckillException;
import com.wzx.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/11 下午8:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class SeckillServiceImplTest {
    private final Logger logger = LoggerFactory.getLogger(SeckillServiceImplTest.class);

    @Autowired
    SeckillService seckillService;

    @Test
    public void getAllSeckill() {
        List<Seckill> allSeckill = seckillService.getAllSeckill();
        logger.info("SeckillList = {}", allSeckill);
    }

    @Test
    public void getById() {
        long seckillId = 1000L;
        Seckill byId = seckillService.getById(seckillId);
        logger.info("seckill={}", byId);
    }

    @Test
    public void seckillTest() {
        long seckillId = 1001L;
        long userPhone = 17349632113L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (!exposer.isExposed()) {
            logger.warn("exposer={}", exposer);
        } else {
            try {
                String md5 = exposer.getMd5();
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("seckillExecution={}", seckillExecution);
            } catch (ClosedSeckillException e1) {
                logger.error(e1.getMessage());
            } catch (RepeatedSeckillException e2) {
                logger.error(e2.getMessage());
            } catch (SeckillException e3) {
                logger.error(e3.getMessage());
            }
        }
    }
}