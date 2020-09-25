package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.Role;
import com.youle.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_USERNAME_FAIL);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
         return userService.findByPage(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody com.youle.pojo.User user){
        try {
            userService.add(user);
            return new Result(true,"添加用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加用户失败");
        }
    }

    @RequestMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(@RequestParam("id")Integer userId){
        try {
            List<Role> roleIds = userService.findRoleIdsByUserId(userId);
            return new Result(true,"查询成功",roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询失败");
        }
    }

    @RequestMapping("/addRoleAndUser")
    public Result addRoleAndUser(@RequestParam("userId")Integer userId, @RequestParam("roleIds") Integer[] roleIds){
        try {
            userService.addRoleAndUser(userId,roleIds);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            com.youle.pojo.User user = userService.findById(id);
            return new Result(true,"查询成功",user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"查询失败");
    }
}
