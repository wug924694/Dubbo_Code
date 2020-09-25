package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.IOrderSettingDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.OrderSetting;
import com.youle.pojo.Setmeal;
import com.youle.service.IOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = IOrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements IOrderSettingService {
    @Autowired
    private IOrderSettingDao orderSettingDao;


    @Override
    public void add(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //判断当前日期是否已具备预约人数，返回根据日期查询的记录数
               long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
               if(count > 0){
                   //根据当前日期查询是否存在记录 如果存在修改可预约人数
                   orderSettingDao.editNumber(orderSetting);
               }else {
                   //如果不存在进行添加
                   orderSettingDao.add(orderSetting);
               }
            }
        }
    }

    @Override
    public List<Map> getOrdersettingByMonth(String date) {
        //取出年月拼接查询条件
        String begin = date +"-1";
        String end = date + "-31";
        Map<String ,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        //调用Dao 根据日期查询预约信息封装list集合
        List<OrderSetting> orderSettings = orderSettingDao.getOrdersettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if(orderSettings != null && orderSettings.size() > 0){
            for (OrderSetting orderSetting : orderSettings) {
                Map<String,Object> m = new HashMap<>();
                m.put("date",orderSetting.getOrderDate().getDate());
                m.put("number",orderSetting.getNumber());
                m.put("reservations",orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //根据当前日期查询是否有预约记录
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        //没有 添加
        if(count < 0){
            orderSettingDao.add(orderSetting);
        }else {
            orderSettingDao.editNumber(orderSetting);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> pages = orderSettingDao.findByCondition(queryString);
        return new PageResult(pages.getTotal(), pages.getResult());
    }

    @Override
    public void confirm(Integer id) {
        orderSettingDao.confirm(id);
    }

    @Override
    public void cancle(Integer id) {
        orderSettingDao.cancle(id);
    }
}
