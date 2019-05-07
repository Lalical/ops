package com.lalic.dao;

import com.lalic.entity.CustomerTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDao
        extends JpaRepository<CustomerTable, String>, JpaSpecificationExecutor<CustomerTable> {

    @Query(value = "SELECT * FROM customer", nativeQuery = true)
    List<CustomerTable> getSimple();


    @Query(value = "SELECT * FROM customer WHERE `id`=:id", nativeQuery = true)
    CustomerTable getByID(@Param(value = "id") String id);

    @Query(value = "UPDATE customer SET phone=:phone,address=:address WHERE `name`=:nam", nativeQuery = true)
    @Modifying
    void update(@Param(value = "nam") String name, @Param(value = "phone") String phone, @Param(value = "address") String address);
}
