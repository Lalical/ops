package com.lalic.controller;

import com.lalic.dao.RightDao;
import com.lalic.entity.RightTable;
import com.lalic.model.BaseResponse;
import com.lalic.model.FinanceEnsureReq;
import com.lalic.model.FinanceSearchReps;
import com.lalic.model.FinanceSearchReq;
import com.lalic.service.FinanceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/finance")
public class FinanceCtr {
    private static final Logger logger = LoggerFactory.getLogger(FinanceCtr.class);

    @Autowired
    FinanceService service;

    @Autowired
    RightDao rightDao;

    @RequestMapping(value = "/searchbytime")
    public FinanceSearchReps searchbytime(@RequestBody FinanceSearchReq req, @RequestHeader String key) {
        if (key == null) {
            return new FinanceSearchReps();
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new FinanceSearchReps();
            else if (!right.getType().contains("财务更新")) {
                return new FinanceSearchReps();
            }
        }
        return service.searchSimple(req);
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
        return service.quicksearch();
    }

    @RequestMapping(value = "/confirm")
    public BaseResponse confirm(@RequestBody FinanceEnsureReq req,@RequestHeader String key) {
        if (key == null) {
            return new BaseResponse().setData("非法操作");
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new BaseResponse().setData("非法操作");
            else if (!right.getType().contains("财务更新")) {
                return new BaseResponse().setData("非法操作");
            }
        }
        int code = service.confirm(req);
        if (code == 1)
            return new BaseResponse().setData("确认成功");
        else
            return new BaseResponse().setData("错误操作");
    }


}
