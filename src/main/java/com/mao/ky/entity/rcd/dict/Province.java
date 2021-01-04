package com.mao.ky.entity.rcd.dict;

import lombok.Getter;
import lombok.Setter;

/**
 * 省市区三级联动数据
 * @author : create by zongx at 2020/11/10 15:19
 */
@Getter
@Setter
public class Province {

    private Long id;            //id
    private Long pid;           //父id
    private String name;        //名称
    private String code;        //邮政编码

}
