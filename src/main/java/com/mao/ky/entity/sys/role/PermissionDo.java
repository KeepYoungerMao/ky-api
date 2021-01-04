package com.mao.ky.entity.sys.role;

import lombok.Getter;
import lombok.Setter;

/**
 * 权限
 * @author : create by zongx at 2020/11/10 10:40
 */
@Getter
@Setter
public class PermissionDo {

    private Long id;            //id
    private Long pid;           //pid
    private String code;        //权限码
    private String name;        //名称

    public PermissionVo toVo() {
        PermissionVo vo = new PermissionVo();
        vo.setId(String.valueOf(this.id));
        vo.setPid(String.valueOf(this.pid));
        vo.setCode(this.code);
        vo.setName(this.name);
        return vo;
    }

}
