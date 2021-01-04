package com.mao.ky.entity.rcd.company;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : create by zongx at 2020/12/4 10:36
 */
@Getter
@Setter
@ToString
public class CompanyVo extends Sign {
    private String id;
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
}
