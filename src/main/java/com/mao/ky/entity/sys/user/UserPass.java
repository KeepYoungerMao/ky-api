package com.mao.ky.entity.sys.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 用户更换密码
 * @author : create by zongx at 2020/11/20 16:56
 */
@Getter
@Setter
public class UserPass {
    @NotNull
    private String username;        //username
    @NotNull
    @Length(min = 4, max = 16)
    private String old_pass;        //old password
    @NotNull
    @Length(min = 4, max = 16)
    private String new_pass;        //new password
}
