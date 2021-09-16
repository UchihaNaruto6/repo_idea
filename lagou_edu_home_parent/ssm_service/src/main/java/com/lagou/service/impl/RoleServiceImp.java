package com.lagou.service.impl;

import com.lagou.dao.ResourceCategoryMapper;
import com.lagou.dao.RoleMapper;
import com.lagou.domain.*;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceCategoryMapper resourceCategoryMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        List<Role> allRole = roleMapper.findAllRole(role);
        return allRole;
    }

    @Override
    public List<String> findMenuByRoleId(Integer roleId) {
        List<String> list = roleMapper.findMenuByRoleId(roleId);
        return list;
    }

    /**
     * 为角色分配菜单
     * @param roleMenuVo
     */
    @Override
    public void RoleContextMenu(RoleMenuVo roleMenuVo) {
        //清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVo.getRoleId());
        //分配菜单关联关系
        for (Integer menuId : roleMenuVo.getMenuIdList()) {
            //补全信息
            Role_menu_relation role_menu_relation=new Role_menu_relation();
            role_menu_relation.setMenuId(menuId);
            role_menu_relation.setRoleId(roleMenuVo.getRoleId());

            Date date=new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);

            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedby("system");

            roleMapper.RoleContextMenu(role_menu_relation);
        }
    }

    @Override
    public void deleteRole(Integer id) {
        //清空中间表
        roleMapper.deleteRoleContextMenu(id);
        //删除角色
        roleMapper.deleteRole(id);
    }

    /**
     * 查询角色对应的资源信息
     * @param roleId
     * @return
     */
    @Override
    public List<ResourceCategory> findResourceListByRoleId(Integer roleId) {
        //1.查询出角色拥有的资源分类信息
        List<Integer> category_id = roleMapper.findResourceCategoryByRoleId(roleId);
        //2.根据查询出的分类id查询具体资源
        ArrayList<ResourceCategory> list = new ArrayList<>();
        for (Integer id : category_id) {
            ResourceCategory resourceCategory = resourceCategoryMapper.findResourceByResourceCategoryId(id);
            list.add(resourceCategory);
        }
        return list;
    }

    /**
     * 为角色分配资源
     * @param roleResourceVo
     */
    @Override
    public void roleContextResource(RoleResourceVo roleResourceVo) {
        //清空当前角色资源的关联关系
        roleMapper.deleteRoleContextResource(roleResourceVo.getRoleId());
        //为角色分配资源
        List<Integer> resourceIdList = roleResourceVo.getResourceIdList();
        for (Integer id : resourceIdList) {
            Role_resource_relation role_resource_relation = new Role_resource_relation();

            role_resource_relation.setResourceId(id);
            role_resource_relation.setRoleId(roleResourceVo.getRoleId());

            Date date=new Date();
            role_resource_relation.setCreatedTime(date);
            role_resource_relation.setUpdatedTime(date);

            role_resource_relation.setCreatedBy("system");
            role_resource_relation.setUpdatedBy("system");

            roleMapper.RoleContextResource(role_resource_relation);
        }
    }
}
