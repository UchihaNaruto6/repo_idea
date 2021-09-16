package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo findAllUserByPage(UserVo userVo) {
        //使用pageHelper
        PageHelper.startPage(userVo.getCurrentPage(),userVo.getPageSize());
        List<User> allUserByPage = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<User>(allUserByPage);
        return pageInfo;
    }

    @Override
    public void updateUserStatus(int id, String status) {
        userMapper.updateUserStatus(id,status);
    }

    @Override
    public User login(User user) {
        User login = userMapper.Login(user);
        try {
            if(login!=null && Md5.verify(user.getPassword(),"login",login.getPassword())){
                return login;
            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Role> findUserRelationRoleById(int id) {
        List<Role> roleList = userMapper.findUserRelationRoleById(id);
        return roleList;

    }

    @Override
    public void userContextRole(UserRoleVo userRoleVo) {
        // 根据用户ID清空中间表的关联关系
        userMapper.deleteUserContextRole(userRoleVo.getUserId());
        //向中间表中添加记录
        for (Integer roleId : userRoleVo.getRoleIdList()) {
            User_Role_relation user_role_relation=new User_Role_relation();

            user_role_relation.setRoleId(roleId);
            user_role_relation.setUserId(userRoleVo.getUserId());

            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }
    }

    /**
     * 获取用户权限信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getUserPermissions(Integer id) {
        //获取当前用户拥有的角色
        List<Role> userRelationRoleById = userMapper.findUserRelationRoleById(id);
        //获取角色id保存到集合中
        List<Integer> ids = new ArrayList<>();
        for (Role role : userRelationRoleById) {
            ids.add(role.getId());
        }
        //获取角色的父级菜单
        List<Menu> parentMenu = userMapper.findParentMenuByRoleId(ids);
        //查询父菜单关联的子菜单
        for (Menu menu : parentMenu) {
            List<Menu> subMenu = userMapper.findSubMenuByPid(menu.getId());
            /*封装子菜单*/
            menu.setSubMenuList(subMenu);
        }
        //获取资源信息
        List<Resource> resources = userMapper.findResourceByRoleId(ids);
        //封装数据并返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("menuList",parentMenu);
        map.put("resourceList",resources);
        return new ResponseResult(true,200,"响应成功！",map);
    }
}
