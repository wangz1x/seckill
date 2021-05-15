package com.wzx.controller;

import com.wzx.dto.Exposer;
import com.wzx.dto.SeckillExecution;
import com.wzx.dto.SeckillResult;
import com.wzx.entity.Seckill;
import com.wzx.enums.SeckillExecutionStateEnum;
import com.wzx.exception.ClosedSeckillException;
import com.wzx.exception.RepeatedSeckillException;
import com.wzx.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author wangzx
 * @email wangzx22@163.com
 * @date 2021/5/14 上午11:14
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(SeckillController.class);

    @Autowired
    SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Seckill> allSeckill = seckillService.getAllSeckill();
        model.addAttribute("list", allSeckill);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill byId = seckillService.getById(seckillId);
        if (byId == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", byId);
        return "detail";
    }

    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,
            produces = {"application/json;charset=utf8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(@PathVariable(name = "seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        if (seckillId == null) {
            result = new SeckillResult<Exposer>(false, "seckillId is null");
            return result;
        }
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            result = new SeckillResult<Exposer>(true, e.getMessage());
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=utf8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execution(@PathVariable("seckillId") Long seckillId,
                                                     @CookieValue(value = "userPhone", required = false) Long userPhone,
                                                     @PathVariable("md5") String md5) {
        SeckillResult<SeckillExecution> result;
        SeckillExecution seckillExecution;
        if (userPhone == null) {
            return new SeckillResult<SeckillExecution>(false, "用户未注册");
        }

        // 该方法抛出的所有RuntimeException都会被Spring处理?
        // 那在调用该方法的时候就不再需要处理各种异常了?
        try {
            seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
        } catch (RepeatedSeckillException e) {
            seckillExecution = new SeckillExecution(seckillId, SeckillExecutionStateEnum.REPEATED);
        } catch (ClosedSeckillException e) {
            seckillExecution = new SeckillExecution(seckillId, SeckillExecutionStateEnum.CLOSED);
        } catch (Exception e) {
            seckillExecution = new SeckillExecution(seckillId, SeckillExecutionStateEnum.INNER_ERROR);
        }
        result = new SeckillResult<SeckillExecution>(true, seckillExecution);
        return result;
    }

    @RequestMapping(value = "/time/now",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> currentTime() {
        Date date = new Date();
        return new SeckillResult<Long>(true, date.getTime());
    }
}
