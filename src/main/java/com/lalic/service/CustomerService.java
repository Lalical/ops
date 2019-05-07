package com.lalic.service;

import com.lalic.entity.CustomerTable;
import com.lalic.model.CustomerInputReq;

import java.util.List;

public interface CustomerService {
    List<CustomerTable> getSimple();

    int input(CustomerInputReq req);
}
