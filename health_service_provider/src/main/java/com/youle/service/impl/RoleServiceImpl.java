package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.RoleDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;
import com.youle.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page = roleDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public List<Permission> findPermissionIdsByRoleId(Integer roleId) {
        return roleDao.findPermissionIdsByRoleId(roleId);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public void addRoleAndPermission(Integer roleId, Integer[] permissionIds) {
        setRoleAndPermission(roleId,permissionIds);
    }

    public void setRoleAndPermission(Integer roleId, Integer[] permissionIds) {
        if (permissionIds != null && permissionIds.length >= 0) {
            for (Integer permissionId : permissionIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("permission_id", permissionId);
                map.put("role_id", roleId);
                roleDao.addRoleAndPermission(map);
            }
        }
    }
}
