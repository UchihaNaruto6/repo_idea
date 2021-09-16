package com.lagou.dao;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuMapper {
    /**
     * 查询全部的父子菜单信息
     * */
    public List<Menu> findSubMenuListByPid(int pid);

    /**
     * 查询菜单列表
     * */
    public List<Menu> findAllMenu();

    /**
     * 根据id查询菜单信息
     * @param id
     * @return
     */
    public Menu findMenuById(int id);
}
