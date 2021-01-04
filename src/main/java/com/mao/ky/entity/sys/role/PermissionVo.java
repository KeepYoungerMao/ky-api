package com.mao.ky.entity.sys.role;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : create by zongx at 2020/11/18 17:22
 */
@Getter
@Setter
public class PermissionVo {
    private String id;            //id
    private String pid;           //pid
    private String code;        //权限码
    private String name;        //名称
}
