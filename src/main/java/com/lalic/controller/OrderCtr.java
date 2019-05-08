package com.lalic.controller;

import com.lalic.dao.RightDao;
import com.lalic.entity.RightTable;
import com.lalic.model.BaseResponse;
import com.lalic.model.FinanceSearchReps;
import com.lalic.model.OrderDelReq;
import com.lalic.model.OrderInputReq;
import com.lalic.model.OrderSearchReq;
import com.lalic.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
public class OrderCtr {
    private static final Logger logger = LoggerFactory.getLogger(OrderCtr.class);

    @Autowired
    OrderService service;

    @Autowired
    RightDao rightDao;

    @RequestMapping(value = "/input", method = RequestMethod.POST)
    public BaseResponse input(@RequestBody OrderInputReq req, @RequestHeader String key) {
        if (key == null) {
            return new BaseResponse().setData("非法操作");
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new BaseResponse().setData("非法操作");
            else if (!right.getType().contains("订单录入")) {
                return new BaseResponse().setData("非法操作");
            }
        }
        int input = service.input(req, key);
        if (input < 0) {
            throw new RuntimeException("录入失败");
        }
        logger.info("input a order success,from:" + key);
        return new BaseResponse().setData("录入成功");
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public FinanceSearchReps search(@RequestBody OrderSearchReq req, @RequestHeader String key) {
        if (key == null) {
            return new FinanceSearchReps();
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new FinanceSearchReps();
            else if (!right.getType().contains("财务更新")) {
                return new FinanceSearchReps();
            }
        }
        return service.simpleSearch(req);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public BaseResponse del(@RequestBody OrderDelReq req, @RequestHeader String key) {
        if (key == null) {
            return new BaseResponse();
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new BaseResponse();
            else if (!right.getType().contains("状态查询")) {
                return new BaseResponse();
            }
        }
        return service.del(req);
    }

    @RequestMapping(value = "/quicksearch", method = RequestMethod.GET)
    public FinanceSearchReps quicksearch(@RequestHeader String key) {
        if (key == null) {
            return new FinanceSearchReps();
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new FinanceSearchReps();
            else if (!right.getType().contains("财务更新")) {
                return new FinanceSearchReps();
            }
        }
        return service.quickSearch();
    }


}
