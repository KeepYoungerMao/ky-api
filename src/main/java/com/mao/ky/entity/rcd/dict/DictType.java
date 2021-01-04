package com.mao.ky.entity.rcd.dict;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典类型
 * @author : create by zongx at 2020/11/10 15:53
 */
@Getter
@Setter
public class DictType {

    private Long id;            //字典类型id
    private String code;        //码
    private String name;        //名称
    private String intro;       //介绍

}
