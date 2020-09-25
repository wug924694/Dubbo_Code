package com.youle.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.OrderSetting;
import com.youle.service.IOrderSettingService;
import com.youle.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private IOrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) throws IOException {
        try {
            //借助工具类读取文件
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettings = new ArrayList<>();
            for (String[] strings : list) {
                String orderDate = strings[0];//可预约时间
                String number = strings[1];//可预约人数
                OrderSetting orderSetting = new OrderSetting(new Date(orderDate),Integer.parseInt(number));
                orderSettings.add(orderSetting);
            }
            //调用service保存信息
            orderSettingService.add(orderSettings);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/getOrdersettingByMonth")
    public Result getOrdersettingByMonth(String date){
        try {
            List<Map> list = orderSettingService.getOrdersettingByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return orderSettingService.findPage(queryPageBean);
    }

    @RequestMapping("/confirm")
    public Result confirm(@RequestParam("id") Integer id){
        try {
            orderSettingService.confirm(id);
            return new Result(true,"预约成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"预约失败");
        }
    }

    @RequestMapping("/cancle")
    public Result cancle(@RequestParam("id") Integer id){
        try {
            orderSettingService.cancle(id);
            return new Result(true,"取消预约成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"取消预约失败");
        }
    }
}
