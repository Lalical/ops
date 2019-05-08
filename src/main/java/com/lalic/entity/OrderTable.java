package com.lalic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ops_order")
@Table(name = "ops_order")
public class OrderTable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "time")
    private String time;

    @Column(name = "customer")
    private String customer;

    @Column(name = "money")
    private String money;

    @Column(name = "customerid")
    private String customerid;

    @Column(name = "ispay")
    private String ispay;

    @Column(name = "paytime")
    private String paytime;

    @Column(name = "payway")
    private String payway;

    @Column(name = "isproensure")
    private String isproensure;

    @Column(name = "isdeliver")
    private String isdeliver;

    @Column(name = "detime")
    private String detime;

    @Column(name = "deno")
    private String deno;

    @Column(name = "fromwho")
    private String fromwho;

    public String getFromwho() {
        return fromwho;
    }

    public void setFromwho(String fromwho) {
        this.fromwho = fromwho;
    }

    public OrderTable() {
    }

    public OrderTable(String id, String time, String customer, String money, String customerid) {
        this.id = id;
        this.time = time;
        this.customer = customer;
        this.money = money;
        this.customerid = customerid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getIsproensure() {
        return isproensure;
    }

    public void setIsproensure(String isproensure) {
        this.isproensure = isproensure;
    }

    public String getIsdeliver() {
        return isdeliver;
    }

    public void setIsdeliver(String isdeliver) {
        this.isdeliver = isdeliver;
    }

    public String getDetime() {
        return detime;
    }

    public void setDetime(String detime) {
        this.detime = detime;
    }

    public String getDeno() {
        return deno;
    }

    public void setDeno(String deno) {
        this.deno = deno;
    }
}
