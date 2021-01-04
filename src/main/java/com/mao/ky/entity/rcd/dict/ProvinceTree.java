package com.mao.ky.entity.rcd.dict;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 省-市-区 树结构
 * 三级联动
 * @author : create by zongx at 2020/11/10 14:58
 */
@Getter
@Setter
public class ProvinceTree {

    private String id;                     //id
    private String code;                    //邮政编码
    private String name;                    //名称
    private List<ProvinceTree> child;       //子类

    public ProvinceTree(Long id, String code, String name) {
        this.id = String.valueOf(id);
        this.code = code;
        this.name = name;
        this.child = new ArrayList<>();
    }

}
