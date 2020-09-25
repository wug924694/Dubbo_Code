package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entiy.Result;
import com.youle.pojo.Setmeal;
import com.youle.service.ISetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private ISetmealService setmealService;

    @RequestMapping("/getAllSetmeal")
    public Result getAllSetmeal(){
        try {
            List<Setmeal> list = setmealService.findAll();
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal  = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
