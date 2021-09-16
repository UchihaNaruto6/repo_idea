package com.lagou.dao;

import com.lagou.domain.Resource;
import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;
import com.lagou.domain.Role_resource_relation;


import java.util.List;

public interface RoleMapper {
    /*
    查询角色列表(条件)
    */
    public List<Role> findAllRole(Role role);
    /*
    根据角色ID查询菜单信息
    */
    List<String> findMenuByRoleId(Integer roleId);
    /**
     * 根据角色id清空关联关系
     */
    public void deleteRoleContextMenu(Integer rid);
    /**
     * 为角色分配菜单信息
     */
    public void RoleContextMenu(Role_menu_relation role_menu_relation);
    /*
    删除角色
    */
    public void deleteRole(Integer id);
    /**
     * 获取当前角色拥有的资源分类信息
     */
    public List<Integer> findResourceCategoryByRoleId(Integer roleId);
    /**
     * 根据角色id清空资源关联关系
     */
    public void deleteRoleContextResource(Integer rid);
    /**
     * 为角色分配资源信息
     */
    public void RoleContextResource(Role_resource_relation role_resource_relation);
}
