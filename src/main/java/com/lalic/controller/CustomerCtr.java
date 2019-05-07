package com.lalic.controller;

import com.lalic.dao.RightDao;
import com.lalic.entity.CustomerTable;
import com.lalic.entity.RightTable;
import com.lalic.model.BaseResponse;
import com.lalic.model.CustomerDetailReps;
import com.lalic.model.CustomerInputReq;
import com.lalic.model.SimpleCustomerReps;
import com.lalic.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerCtr {
    private static final Logger logger = LoggerFactory.getLogger(CustomerCtr.class);

    @Autowired
    CustomerService service;

    @Autowired
    RightDao rightDao;

    @RequestMapping(value = "/simple",method = RequestMethod.GET)
    public SimpleCustomerReps getSimple() {
        List<CustomerTable> simple = service.getSimple();
        SimpleCustomerReps reps=new SimpleCustomerReps();
        reps.setCustomer(simple);
        return reps;
    }

    @RequestMapping(value = "/input")
    public BaseResponse input(@RequestBody CustomerInputReq req,@RequestHeader String key) {

        if (key == null) {
            return new BaseResponse().setData("非法操作");
        } else {
            RightTable right = rightDao.getRight(key);
            if (right == null) return new BaseResponse().setData("非法操作");
            else if (!right.getType().contains("订单录入")) {
                return new BaseResponse().setData("非法操作");
            }
        }

        int code = service.input(req);

        return new BaseResponse().setData("录入成功");
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public CustomerDetailReps getDetail() {
        List<CustomerTable> simple = service.getSimple();
        CustomerDetailReps ret=new CustomerDetailReps();
        for (CustomerTable customerTable : simple) {
            CustomerDetailReps.Data item=new CustomerDetailReps.Data();
            item.setAddress(customerTable.getAddress());
            item.setName(customerTable.getName());
            ret.add(item);
        }

        return ret;
    }


}
