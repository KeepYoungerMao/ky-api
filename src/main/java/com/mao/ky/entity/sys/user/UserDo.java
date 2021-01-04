package com.mao.ky.entity.sys.user;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户 sys_user
 * 实现OAuth2
 * @author : create by zongx at 2020/11/10 10:28
 */
@Getter
@Setter
@ToString
public class UserDo extends Sign {

    private Long id;                //id
    private String username;        //登录名
    private String password;        //密码
    private Boolean expired;        //是否过期
    private Boolean locked;         //是否锁定
    private Boolean enabled;        //是否可使用
    private Long client;          //客户端id
    private String client_name;
    private Long role;            //角色id
    private String role_name;
    private String avatar;          //头像

    public KyUserDetail toDetail() {
        KyUserDetail userDetail = new KyUserDetail();
        userDetail.setUsername(this.username);
        userDetail.setPassword(this.password);
        userDetail.setExpired(this.expired);
        userDetail.setLocked(this.locked);
        userDetail.setEnabled(this.enabled);
        userDetail.setClient(String.valueOf(this.client));
        return userDetail;
    }

    public UserVo toVo() {
        UserVo vo = new UserVo();
        vo.setId(String.valueOf(this.id));
        vo.setUsername(this.username);
        vo.setExpired(this.expired);
        vo.setLocked(this.locked);
        vo.setEnabled(this.enabled);
        vo.setClient(String.valueOf(this.client));
        vo.setClient_name(this.client_name);
        vo.setRole(String.valueOf(this.role));
        vo.setRole_name(this.role_name);
        vo.setAvatar(this.avatar);
        Sign.sign(vo, this);
        return vo;
    }

}
