package com.mao.ky.entity.rcd.company;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : create by zongx at 2020/12/4 10:35
 */
@Getter
@Setter
@ToString
public class CompanyDo extends Sign {
    private Long id;
    private String name;
    private String code;
    private String intro;
    private String phone;
    private String email;
    private String web;
    private Integer province;
    private Integer city;
    private Integer area;
    private String address;

    public CompanyCache toCache() {
        CompanyCache cache = new CompanyCache();
        cache.setId(this.id);
        cache.setName(this.name);
        cache.setCode(this.code);
        return cache;
    }

    public CompanyVo toVo() {
        CompanyVo vo = new CompanyVo();
        vo.setId(String.valueOf(this.id));
        vo.setName(this.name);
        vo.setCode(this.code);
        vo.setIntro(this.intro);
        vo.setPhone(this.phone);
        vo.setEmail(this.email);
        vo.setWeb(this.web);
        vo.setProvince(this.province);
        vo.setCity(this.city);
        vo.setArea(this.area);
        vo.setAddress(this.address);
        Sign.sign(vo,this);
        return vo;
    }
}
