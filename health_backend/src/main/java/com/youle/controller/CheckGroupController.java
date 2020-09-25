package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.CheckGroup;
import com.youle.pojo.CheckItem;
import com.youle.service.ICheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private ICheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam("itemIds") Integer[] checkItemIds){
        try {
           checkGroupService.add(checkGroup,checkItemIds);
           return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.pageQuery(queryPageBean);
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer checkGroupId){
        try {
            CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer checkGroupId){
        try {
            List<CheckItem> checkitemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, @RequestParam("itemIds") Integer[] checkItemIds){
        try {
            checkGroupService.edit(checkGroup,checkItemIds);
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam("id") Integer checkGroupId){
        try {
            checkGroupService.delete(checkGroupId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}
