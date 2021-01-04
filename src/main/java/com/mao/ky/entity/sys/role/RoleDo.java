package com.mao.ky.entity.sys.role;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据
 * @author : create by zongx at 2020/11/10 16:37
 */
@Getter
@Setter
public class RoleDo extends Sign {
    private Long id;
    private String code;
    private String name;
    private String intro;

    public RoleVo toVo(List<Long> ids) {
        RoleVo vo = new RoleVo();
        vo.setId(String.valueOf(this.id));
        vo.setCode(this.code);
        vo.setName(this.name);
        vo.setIntro(this.intro);
        List<String> permissions = new ArrayList<>();
        ids.forEach(id -> permissions.add(String.valueOf(id)));
        vo.setPermissions(permissions);
        Sign.sign(vo,this);
        return vo;
    }

    public RoleVo toVo() {
        RoleVo vo = new RoleVo();
        vo.setId(String.valueOf(this.id));
        vo.setCode(this.code);
        vo.setName(this.name);
        vo.setIntro(this.intro);
        Sign.sign(vo,this);
        return vo;
    }
}
