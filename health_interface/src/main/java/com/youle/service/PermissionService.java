package com.youle.service;

import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;

import java.util.List;

public interface PermissionService {

    void add(Permission permission);

    PageResult findByPage(QueryPageBean queryPageBean);

    List<Role> findAll();

    Permission findById(Integer id);

}
