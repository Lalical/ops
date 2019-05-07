package com.lalic.dao;

import com.lalic.entity.OrderTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao
        extends JpaRepository<OrderTable, String>, JpaSpecificationExecutor<OrderTable> {

    @Query(value = "SELECT * FROM ops_order WHERE `time`>=:starttime AND `time`<=:endtime ", nativeQuery = true)
    List<OrderTable> byTime(@Param(value = "starttime") long starttime, @Param(value = "endtime") long endtime);


    @Query(value = "SELECT * FROM ops_order WHERE ispay='未支付'", nativeQuery = true)
    List<OrderTable> byPay();


    @Query(value = "UPDATE ops_order SET paytime=:paytime, payway=:payway,ispay='已支付' WHERE `id`=:orderid", nativeQuery = true)
    @Modifying
    int financeConfirm(@Param(value = "orderid") String orderid, @Param(value = "paytime") String paytime, @Param(value = "payway") String payway);

    @Query(value = "SELECT * FROM ops_order WHERE ispay='未支付'", nativeQuery = true)
    List<OrderTable> quickFinanceSearch();

    @Query(value = "SELECT * FROM ops_order WHERE isdeliver != '已配送'", nativeQuery = true)
    List<OrderTable> deliverQuickSearch();


    @Query(value = "UPDATE ops_order SET isdeliver='已配送',deno=:deno,detime=:detime WHERE `id`=:orderid", nativeQuery = true)
    @Modifying
    int finish(@Param(value = "orderid") String orderid, @Param(value = "deno") String deno, @Param(value = "detime") String detime);

    @Query(value = "UPDATE ops_order SET isdeliver='部分配送',deno=:deno,detime=:detime WHERE `id`=:orderid", nativeQuery = true)
    @Modifying
    int finishPart(@Param(value = "orderid") String orderid, @Param(value = "deno") String deno, @Param(value = "detime") String detime);

    @Query(value = "SELECT * FROM ops_order WHERE `time`>=:starttime AND `time`<=:endtime AND customer=:customer", nativeQuery = true)
    List<OrderTable> byTimeAndName(@Param(value = "starttime") long starttime, @Param(value = "endtime") long endtime, @Param(value = "customer") String customer);
}
