package com.mao.ky.service.auth;

import com.mao.ky.entity.sys.user.KyUserDetail;
import com.mao.ky.entity.sys.user.UserDo;
import com.mao.ky.mapper.sys.RolePermissionMapper;
import com.mao.ky.mapper.sys.UserMapper;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户数据查询
 * 继承自 OAuth2
 * @author : create by zongx at 2020/10/15 17:30
 */
@Service
public class JdbcUserDetailsService implements UserDetailsService {

    private UserMapper userMapper;
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    public void setRolePermissionMapper(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    /**
     * 根据用户名查询用户详情
     * 返回继承自 UserDetails 的用户类 User
     * 用户与角色为多对多关系，查询权限的时候根据用户关联的多个角色id查询，去重
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (SU.isEmpty(username))
            return null;
        //获取用户信息
        UserDo userDo = userMapper.getUserByUsername(username);
        if (null == userDo || null == userDo.getId())
            return null;
        KyUserDetail userDetail = userDo.toDetail();
        //获取该用户目前所拥有的权限信息
        List<String> permissions = rolePermissionMapper.getPermissionByRoleId(userDo.getRole());
        //组装数据
        List<GrantedAuthority> list = new ArrayList<>();
        permissions.forEach(s -> list.add((GrantedAuthority) () -> s));
        userDetail.setAuthorities(list);
        return userDetail;
    }

}
