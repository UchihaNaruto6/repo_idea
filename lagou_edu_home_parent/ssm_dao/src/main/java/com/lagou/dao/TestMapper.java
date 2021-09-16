package com.lagou.dao;

import com.lagou.domain.Test;

import java.util.List;

public interface TestMapper {
    /**
     * 查询所有test操作
     */
    public List<Test> findAll();
}
