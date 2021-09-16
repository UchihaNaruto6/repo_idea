package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.*;

import java.util.List;

public interface UserService {
    /*
    查询所有用户
    */
    public PageInfo findAllUserByPage(UserVo userVo);
    /*
     * 修改用户状态
     * */
    public void updateUserStatus(int id, String status);
    /*
    用户登录
    */
    public User login(User user);
    /**
     * 获取用户拥有的角色
     * */
    public List<Role> findUserRelationRoleById(int id) ;
    /*
    用户关联角色
    */
    public void userContextRole(UserRoleVo userRoleVo);
    /**
     * 获取用户权限，进行菜单动态展示
     */
    public ResponseResult getUserPermissions(Integer id);
}
