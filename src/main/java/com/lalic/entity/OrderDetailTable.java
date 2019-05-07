package com.lalic.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "orderdetail")
@Table(name = "orderdetail")
public class OrderDetailTable {

    @Id
    @Column(name = "id")
    private String id = UUID.randomUUID().toString().replaceAll("-", "");

    @Column(name = "orderid")
    private String orderid;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "count")
    private String count;

    @Column(name = "perprice")
    private String perprice;

    @Column(name = "attr")
    private String attr;

    @Column(name = "mess")
    private String mess;

    @Column(name = "delivercount")
    private String delivercount;


    public OrderDetailTable(String orderid, String name, String count, String perprice, String attr, String mess) {
        this.orderid = orderid;
        this.name = name;
        this.count = count;
        this.perprice = perprice;
        this.attr = attr;
        this.mess = mess;
    }

    public OrderDetailTable() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPerprice() {
        return perprice;
    }

    public void setPerprice(String perprice) {
        this.perprice = perprice;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getDelivercount() {
        return delivercount;
    }

    public void setDelivercount(String delivercount) {
        this.delivercount = delivercount;
    }
}
