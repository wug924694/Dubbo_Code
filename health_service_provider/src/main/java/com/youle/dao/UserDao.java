package com.youle.dao;

import com.github.pagehelper.Page;
import com.youle.pojo.Role;
import com.youle.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User findByUsername(String username);

    Page<User> findByCondition(String queryString);

    void add(User user);

    List<Role> findRoleIdsByUserId(Integer userId);


    void addUserAndRole(Map<String, Integer> map);

    User findById(Integer id);


}
