package com.mao.ky.entity.sys.role;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 角色展现数据
 * @author : create by zongx at 2020/11/10 16:38
 */
@Getter
@Setter
public class RoleVo extends Sign {
    private String id;
    private String code;
    private String name;
    private String intro;

    private List<String> permissions;
}
