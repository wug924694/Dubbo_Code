package com.youle.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.CheckItem;
import com.youle.service.ICheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private ICheckItemService checkItemService;

    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){

        return checkItemService.findPage(queryPageBean);
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(@RequestParam("id") Integer checkItemId){

        try {
            checkItemService.delete(checkItemId);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer checkItemId){
        try {
            CheckItem checkItem = checkItemService.findById(checkItemId);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try {
            List<CheckItem> checkItems = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
