package com.lalic.iml;

import com.lalic.dao.CustomerDao;
import com.lalic.entity.CustomerTable;
import com.lalic.model.CustomerInputReq;
import com.lalic.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerImp implements CustomerService {

    @Autowired
    CustomerDao dao;

    @Override
    public List<CustomerTable> getSimple() {
        return dao.getSimple();
    }

    @Override
    public int input(CustomerInputReq req) {
        CustomerTable one = new CustomerTable();
        one.setAddress(req.getAddress());
        one.setPhone(req.getPhone());
        one.setName(req.getName());
        dao.save(one);
        return 0;
    }
}
