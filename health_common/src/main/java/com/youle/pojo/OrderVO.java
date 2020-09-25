package com.youle.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderVO implements Serializable {
    public static final String ORDERTYPE_TELEPHONE = "电话预约";
    public static final String ORDERTYPE_WEIXIN = "微信预约";
    public static final String ORDERSTATUS_YES = "已到诊";
    public static final String ORDERSTATUS_NO = "未到诊";

    private Integer id;
    private Date orderDate;//预约日期
    private String orderType;//预约类型 电话预约/微信预约
    private String orderStatus;//预约状态（是否到诊）
    private String name;//姓名
    private String phoneNumber;//手机号
    private String setmealName;//套餐名

    public OrderVO() {
    }

    public OrderVO(Integer id, Date orderDate, String orderType, String orderStatus, String name, String phoneNumber, String setmealName) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.orderStatus = orderStatus;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.setmealName = setmealName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static String getOrdertypeTelephone() {
        return ORDERTYPE_TELEPHONE;
    }

    public static String getOrdertypeWeixin() {
        return ORDERTYPE_WEIXIN;
    }

    public static String getOrderstatusYes() {
        return ORDERSTATUS_YES;
    }

    public static String getOrderstatusNo() {
        return ORDERSTATUS_NO;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSetmealName() {
        return setmealName;
    }

    public void setSetmealName(String setmealName) {
        this.setmealName = setmealName;
    }

}
