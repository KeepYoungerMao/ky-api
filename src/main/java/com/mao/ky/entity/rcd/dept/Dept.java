package com.mao.ky.entity.rcd.dept;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author : create by zongx at 2020/12/3 17:27
 */
@Getter
@Setter
public class Dept {
    private String id;                //id
    @NotNull
    private String pid;               //pid
    @NotNull
    private String cid;               //company id
    @NotNull
    @Length(min = 1, max = 50)
    private String name;            //name
    @NotNull
    @Length(min = 1, max = 20)
    private String code;            //code
    @Length(max = 20)
    private String phone;           //phone
    @Length(max = 500)
    private String intro;           //intro
    private Long time;              //成立时间
    @Length(max = 200)
    private String remark;          //remark

    public DeptDo toDo() {
        DeptDo dd = new DeptDo();
        dd.setName(this.name);
        dd.setCode(this.code);
        dd.setPhone(this.phone);
        dd.setIntro(this.intro);
        dd.setTime(this.time);
        dd.setRemark(this.remark);
        Sign.sign(dd);
        return dd;
    }
}
