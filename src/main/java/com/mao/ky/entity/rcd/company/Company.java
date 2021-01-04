package com.mao.ky.entity.rcd.company;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author : create by zongx at 2020/12/4 10:35
 */
@Getter
@Setter
public class Company {
    private String id;
    @NotNull
    @Length(min = 1, max = 100)
    private String name;
    @NotNull
    @Length(min = 1, max = 50)
    private String code;
    @Length(max = 500)
    private String intro;
    @Length(max = 20)
    private String phone;
    @Length(max = 50)
    private String email;
    @Length(max = 50)
    private String web;
    @NotNull
    private Integer province;
    @NotNull
    private Integer city;
    @NotNull
    private Integer area;
    @Length(max = 200)
    private String address;

    public CompanyDo toDo() {
        CompanyDo cd = new CompanyDo();
        cd.setName(this.name);
        cd.setCode(this.code);
        cd.setIntro(this.intro);
        cd.setPhone(this.phone);
        cd.setWeb(this.web);
        cd.setEmail(this.email);
        cd.setProvince(this.province);
        cd.setCity(this.city);
        cd.setArea(this.area);
        cd.setAddress(this.address);
        Sign.sign(cd);
        return cd;
    }
}
