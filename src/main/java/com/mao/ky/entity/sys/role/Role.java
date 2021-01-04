package com.mao.ky.entity.sys.role;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author : create by zongx at 2020/11/18 16:20
 */
@Getter
@Setter
public class Role {
    private String id;
    @NotNull
    @Length(min = 1, max = 20)
    private String code;
    @NotNull
    @Length(min = 1, max = 20)
    private String name;
    @Length(max = 200)
    private String intro;

    private List<String> inc_permissions;   //需要增加的权限列表
    private List<String> dec_permissions;   //需要去掉的权限列表

    public RoleDo toDo() {
        RoleDo roleDo = new RoleDo();
        roleDo.setCode(this.code);
        roleDo.setName(this.name);
        roleDo.setIntro(this.intro);
        Sign.sign(roleDo);
        return roleDo;
    }
}
