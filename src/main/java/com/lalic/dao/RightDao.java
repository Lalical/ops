package com.lalic.dao;

import com.lalic.entity.RightTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RightDao
        extends JpaRepository<RightTable, String>, JpaSpecificationExecutor<RightTable> {

    @Query(value = "SELECT * FROM ops_right WHERE `key`=:key", nativeQuery = true)
    RightTable getRight(@Param(value = "key") String key);

}
