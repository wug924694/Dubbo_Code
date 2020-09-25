package com.youle.service;

import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;

import java.util.List;

public interface RoleService {

    PageResult findByPage(QueryPageBean queryPageBean);

    void add(Role role);

    List<Role> findAll();

    List<Permission> findPermissionIdsByRoleId(Integer roleId);

    Role findById(Integer id);

    void addRoleAndPermission(Integer roleId, Integer[] permissionIds);
}
