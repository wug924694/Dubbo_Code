package com.youle.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.pojo.Permission;
import com.youle.pojo.Role;
import com.youle.pojo.User;
import com.youle.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if(user == null){
            //用户名不存在
            return  null;
        }
        //获取用户关联的角色
        List<GrantedAuthority> list = new ArrayList<>();
        //动态给用户授权
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            //授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            //获取角色所关联的所有资源权限
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                //授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        return userDetails;
    }
}
