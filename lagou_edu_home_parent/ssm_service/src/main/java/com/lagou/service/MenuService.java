package com.lagou.service;

import com.lagou.domain.Menu;

import java.util.List;

public interface MenuService {
    /**
     * 查询父子级菜单列表
     * @param pid
     * @return
     */
    public List<Menu> findSubMenuListByPid(int pid);

    /**
     * 查询所有菜单列表信息
     * @return
     */
    public List<Menu> findAllMenu();

    /**
     * 根据id查询菜单信息
     * @param id
     * @return
     */
    public Menu findMenuById(int id);
}
