package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.PermissionDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;
import com.youle.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page = permissionDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Role> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }




}
