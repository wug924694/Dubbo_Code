package com.youle.dao;

import com.github.pagehelper.Page;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {

    Set<Role> findByUserId(Integer userId);

    Page<User> findByCondition(String queryString);

    void add(Role role);

    List<Role> findAll();

    List<Permission> findPermissionIdsByRoleId(Integer roleId);

    Role findById(Integer id);

    void addRoleAndPermission(Map<String, Integer> map);
}
