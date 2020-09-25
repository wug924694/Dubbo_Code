package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youle.dao.MemberDao;
import com.youle.dao.OrderDao;
import com.youle.service.ReportService;
import com.youle.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        try {
            Map<String,Object> map = new HashMap<>();
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            //获取本周第一天的日期
            String thisweekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            //获取本月第一天的日期
            String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            //日期
            map.put("reportDate", today);
            //本日新增会员数
            Integer todayNewMember = memberDao.findMemberCountByDate(today);
            map.put("todayNewMember",todayNewMember);
            //总会员数
            Integer totalMember = memberDao.findMemberTotalCount();
            map.put("totalMember",totalMember);
            //本周新增会员数
            Integer thisWeekNewMember  = memberDao.findMemberCountAfterDate(thisweekMonday);
            map.put("thisWeekNewMember",thisWeekNewMember);
            //本月新增会员数
            Integer thisMonthNewMember  = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
            map.put("thisMonthNewMember",thisMonthNewMember);
            //今日预约数
            Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
            map.put("todayOrderNumber",todayOrderNumber);
            //本周预约数
            Integer thisweekOrderNumber = orderDao.findOrderCountAfterDate(thisweekMonday);
            map.put("thisWeekOrderNumber",thisweekOrderNumber);
            //本月预约数
            Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            //今日到诊数
            Integer todayVisitsNumber=orderDao.findVisitsCountByDate(today);
            map.put("todayVisitfindMemberCountBeforeDatesNumber",todayVisitsNumber);
            //本周到诊数
            Integer thisweekVisitsNumber= orderDao.findVisitsCountAfterDate(thisweekMonday);
            map.put("thisWeekVisitsNumber",thisweekVisitsNumber);
            //本月预约到诊数
            Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            //热门套餐
            List<Map> hotSetmeal = orderDao.findHotSetmeal();
            map.put("hotSetmeal",hotSetmeal);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
