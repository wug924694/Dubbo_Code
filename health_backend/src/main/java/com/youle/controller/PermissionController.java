package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.service.PermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        try {
            permissionService.add(permission);
            return new Result(true,"新增权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"新增权限失败");
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return permissionService.findByPage(queryPageBean);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<Role> list = permissionService.findAll();
            return new Result(true,"查询成功",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"查询失败");
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            Permission permission = permissionService.findById(id);
            return new Result(true,"查询成功",permission);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"查询失败");
    }


}

