package com.mao.ky.entity.sys.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

/**
 * @author : create by zongx at 2020/11/18 14:13
 */
@Getter
@Setter
public class KyUserDetail implements UserDetails, Serializable {

    private static final long serialVersionUID = 12345467890L;

    private String username;        //登录名
    private String password;        //密码
    private Boolean expired;        //是否过期
    private Boolean locked;         //是否锁定
    private Boolean enabled;        //是否可使用
    private String client;          //客户端名称

    private List<GrantedAuthority> authorities;     //权限列表

    @Override
    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.expired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
