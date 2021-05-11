package com.wzx.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/11 下午8:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillServiceImplTest {

    @Autowired
    SeckillServiceImpl seckillService;

    @Test
    public void getAllSeckill() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void exportSeckillUrl() {
    }

    @Test
    public void executeSeckill() {
    }
}