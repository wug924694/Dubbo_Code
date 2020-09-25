package com.youle.dao;


import com.github.pagehelper.Page;
import com.youle.pojo.OrderSetting;
import com.youle.pojo.Setmeal;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface IOrderSettingDao {


    void add(OrderSetting orderSetting);

    void editNumber(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    List<OrderSetting> getOrdersettingByMonth(Map map);

    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);
    //根据预约日期查询预约设置信息
    public OrderSetting findByOrderDate(Date orderDate);

    Page<Setmeal> findByCondition(String queryString);

    void confirm(Integer id);

    void cancle(Integer id);
}
