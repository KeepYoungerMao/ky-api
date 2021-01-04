package com.mao.ky.entity.sys.user;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户展现数据
 * @author : create by zongx at 2020/11/10 16:32
 */
@Getter
@Setter
public class UserVo extends Sign {

    private String id;                //id
    private String username;        //登录名
    private Boolean expired;        //是否过期
    private Boolean locked;         //是否锁定
    private Boolean enabled;        //是否可使用
    private String client;          //客户端id
    private String client_name;
    private String role;            //角色id
    private String role_name;       //
    private String avatar;          //头像

}
