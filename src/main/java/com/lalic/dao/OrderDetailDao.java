package com.lalic.dao;

import com.lalic.entity.OrderDetailTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailDao
        extends JpaRepository<OrderDetailTable, String>, JpaSpecificationExecutor<OrderDetailTable> {

    @Query(value = "SELECT * FROM orderdetail WHERE orderid=:orderid", nativeQuery = true)
    List<OrderDetailTable> byOrderId(@Param(value = "orderid") String orderid);

    @Query(value = "SELECT * FROM orderdetail WHERE `id`=:id", nativeQuery = true)
    OrderDetailTable byId(@Param(value = "id") String id);

    @Query(value = "UPDATE orderdetail SET delivercount=:delivercount WHERE `id`=:id", nativeQuery = true)
    @Modifying
    int send(@Param(value = "id") String id, @Param(value = "delivercount") String delivercount);


    @Query(value = "SELECT count(*) FROM (SELECT a.count,b.delivercount FROM orderdetail a JOIN orderdetail b ON a.count=b.delivercount WHERE a.orderid=:orderid AND b.orderid=:orderid) AS B", nativeQuery = true)
    int judgeDeliver(@Param(value = "orderid") String orderid);

    @Query(value = "SELECT count(*) FROM orderdetail WHERE orderid=:orderid", nativeQuery = true)
    int countOrderDetail(@Param(value = "orderid") String orderid);

}
