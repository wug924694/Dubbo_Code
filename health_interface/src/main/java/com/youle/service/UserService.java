package com.youle.service;

import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Role;
import com.youle.pojo.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    PageResult findByPage(QueryPageBean queryPageBean);

    void add(User user);

    List<Role> findRoleIdsByUserId(Integer userId);

    void addRoleAndUser(Integer userId, Integer[] roleIds);

    User findById(Integer id);
}
