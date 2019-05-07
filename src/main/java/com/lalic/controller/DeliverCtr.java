package com.lalic.controller;

import com.lalic.dao.RightDao;
import com.lalic.entity.RightTable;
import com.lalic.model.BaseResponse;
import com.lalic.model.DeliverSearchReps;
import com.lalic.model.DeliverSendReq;
import com.lalic.model.SimpleSearchReq;
import com.lalic.service.DeliverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/deliver")
public class DeliverCtr {
    private static final Logger logger = LoggerFactory.getLogger(DeliverCtr.class);

    @Autowired
    DeliverService service;

    @Autowired
    RightDao rightDao;

    @RequestMapping(value = "/searchbytime")
    public DeliverSearchReps searchbytime(@RequestBody SimpleSearchReq req) {
        return service.searchSimple(req);
    }

    @RequestMapping(value = "/quicksearch", method = RequestMethod.GET)
    public DeliverSearchReps quicksearch() {
        return service.quicksearch();
    }

    @RequestMapping(value = "/send")
    public BaseResponse send(@RequestBody DeliverSendReq req,@RequestHeader String key) {

        if (key == null) {
            return new BaseResponse().setData("非法操作");
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new BaseResponse().setData("非法操作");
            else if (!right.getType().contains("配送更新")) {
                return new BaseResponse().setData("非法操作");
            }
        }
        int code = service.send(req);
        return new BaseResponse().setData("配送信息录入成功");
    }


}
