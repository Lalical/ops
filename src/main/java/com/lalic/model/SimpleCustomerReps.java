package com.lalic.model;

import com.lalic.entity.CustomerTable;

import java.util.List;

public class SimpleCustomerReps {

    List<CustomerTable> customer;

    public List<CustomerTable> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerTable> customer) {
        this.customer = customer;
    }
}
