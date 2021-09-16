package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户分页&多条件查询
     * @param userVo
     * @return
     */
    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo){
        PageInfo pageInfo = userService.findAllUserByPage(userVo);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",pageInfo);
        List<User> list = pageInfo.getList();
        System.out.println(list);
        return responseResult;
    }

    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(@RequestParam int id ,@RequestParam String status){
        if("ENABLE".equalsIgnoreCase(status)){
            status = "DISABLE";
        }else{
            status = "ENABLE";
        }
        userService.updateUserStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",status);
        return responseResult;
    }
    /**
     * 用户登录
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request){
        User user1 = userService.login(user);
        if(user1!=null){
            //表示登录成功
            //保存用户ID和access_token到session中 主要是实现记住密码登录功能
            HttpSession session = request.getSession();
            String token = UUID.randomUUID().toString();
            session.setAttribute("access_token",token);
            session.setAttribute("user_id",user1.getId());
            //将查询出的信息响应出去
            HashMap<String, Object> map = new HashMap<>();
            map.put("access_token",token);
            map.put("user_id",user1.getId());
            //将查询出来的user也封装到响应中
            map.put("user",user1);
            return new ResponseResult(true,1,"登陆成功！",map);
        }else {
            //表示登录失败
            return new ResponseResult(true,400,"用户名或者密码错误！",null);
        }
    }

    /**
     * 回显当前用户的角色
     */
    @RequestMapping("/findUserRoleById")
    public ResponseResult findUserRoleById(int id){
        List<Role> roleList = userService.findUserRelationRoleById(id);
        return new ResponseResult(true,200,"分配角色回显成功",roleList);
    }

    /**
     * 分配角色
     */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserRoleVo userRoleVo){
        userService.userContextRole(userRoleVo);
        return new ResponseResult(true,200,"分配角色成功",null);
    }

    /**
     * 获取用户的权限信息
     */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPerssions(HttpServletRequest request){
        //获取请求头中的token
        String header_token = request.getHeader("Authorization");
        System.out.println(header_token);
        //获取session中的token
        String token = (String) request.getSession().getAttribute("access_token");
        System.out.println(token);
        //判断session是否一致
        if(header_token==token){
            //表示登录状态
            //取出id，进行权限获取
            Integer userId = (Integer) request.getSession().getAttribute("user_id");
            ResponseResult responseResult = userService.getUserPermissions(userId);
            return responseResult;
        }else {
            //登录异常
            return new ResponseResult(false,400,"获取菜单失败",null);
        }
    }

}
