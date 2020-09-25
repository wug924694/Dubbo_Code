package com.youle.service;

import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface IOrderSettingService {


    void add(List<OrderSetting> orderSettings);

    List<Map> getOrdersettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);

    PageResult findPage(QueryPageBean queryPageBean);

    void confirm(Integer id);

    void cancle(Integer id);
}
