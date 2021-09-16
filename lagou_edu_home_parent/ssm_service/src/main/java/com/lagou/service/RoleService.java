package com.lagou.service;

import com.lagou.domain.ResourceCategory;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.domain.RoleResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RoleService {
    /**
     * 条件查询角色列表信息
     * @param role
     * @return
     */
    public List<Role> findAllRole(Role role);
    /**
     * 根据ID查询角色关联菜单
     * */
    List<String> findMenuByRoleId(Integer roleId);

    /**
     * 为角色关联菜单
     * @param roleMenuVo
     */
    public void RoleContextMenu(RoleMenuVo roleMenuVo);

    /**
     * 删除角色信息
     * @param id
     */
    public void deleteRole(Integer id);
    /**
     * 查询角色拥有的资源信息
     */
    public List<ResourceCategory> findResourceListByRoleId(Integer roleId);
    /**
     * 为角色关联资源
     */
    public void roleContextResource(RoleResourceVo roleResourceVo);
}
