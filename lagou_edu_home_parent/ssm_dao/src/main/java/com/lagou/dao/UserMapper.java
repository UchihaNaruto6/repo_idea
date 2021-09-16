package com.lagou.dao;

import com.lagou.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /*
    多条件查询所有用户
    */
    public List<User> findAllUserByPage(UserVo userVo);
    /**
     * 修改用户状态
     * */
    public void updateUserStatus(@Param("id") int id, @Param("status") String status);
    /**
     * 用户登录（根据用户名查询用户信息）
     */
    public User Login(User user);
    /**
     * 根据ID查询用户当前角色
     * */
    public List<Role> findUserRelationRoleById(int id);
    /*
    根据用户ID清空中间表
    */
    public void deleteUserContextRole(Integer userId);
    /*
    分配角色
    */
    public void userContextRole(User_Role_relation user_role_relation);
    /**
     * 根据角色id查询父级（顶级）菜单信息(-1)
     */
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);
    /**
     * 根据pid父级菜单id查询子级菜单信息
     */
    public List<Menu> findSubMenuByPid(Integer pid);
    /**
     * 获取用户拥有的资源权限信息
     */
    public List<Resource> findResourceByRoleId(List<Integer> ids);
}
