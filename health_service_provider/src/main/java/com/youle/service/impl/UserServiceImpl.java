package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.PermissionDao;
import com.youle.dao.RoleDao;
import com.youle.dao.UserDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;
import com.youle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if(user == null){
            return null;
        }
        //根据用户查询相应的用户信息 根据用户id查询用户角色
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findByUserId(userId);
        if(roles != null && roles.size() > 0){
            for (Role role : roles) {
                Integer roleId = role.getId();
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                if(permissions != null && permissions.size() > 0){
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page = userDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public List<Role> findRoleIdsByUserId(Integer userId) {
        return userDao.findRoleIdsByUserId(userId);
    }

    @Override
    public void addRoleAndUser(Integer userId,Integer[] roleIds) {
        setUserAndRole(userId,roleIds);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }


    public void setUserAndRole(Integer userId, Integer[] roleIds) {
        if (roleIds != null && roleIds.length >= 0) {
            for (Integer roleId : roleIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("role_id", roleId);
                map.put("user_id", userId);
                userDao.addUserAndRole(map);
            }
        }
    }
}
