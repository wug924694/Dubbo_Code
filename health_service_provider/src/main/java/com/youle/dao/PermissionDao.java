package com.youle.dao;

import com.github.pagehelper.Page;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionDao {

    Set<Permission>  findByRoleId(Integer roleId);

    void add(Permission permission);

    Page<User> findByCondition(String queryString);

    List<Role> findAll();

    Permission findById(Integer id);


}
