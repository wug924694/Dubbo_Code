package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return roleService.findByPage(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Role role){
        try {
            roleService.add(role);
            return new Result(true,"添加用户成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加用户失败");
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Role> list = roleService.findAll();
            return new Result(true,"查询成功",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"查询失败");
    }

    @RequestMapping("/findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(@RequestParam("id")Integer roleId){
        try {
            List<Permission> permissionIds = roleService.findPermissionIdsByRoleId(roleId);
            return new Result(true,"查询成功",permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询失败");
        }
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            Role role = roleService.findById(id);
            return new Result(true,"查询成功",role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"查询失败");
    }


    @RequestMapping("/addRoleAndPermission")
    public Result addRoleAndPermission(@RequestParam("roleId")Integer roleId, @RequestParam("permissionIds") Integer[] permissionIds){
        try {
            roleService.addRoleAndPermission(roleId,permissionIds);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加失败");
        }
    }
}
