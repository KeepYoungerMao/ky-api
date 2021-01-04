package com.mao.ky.entity.rcd.dict;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典展现数据
 * @author : create by zongx at 2020/11/10 16:00
 */
@Getter
@Setter
public class DictVo extends Sign {

    private String id;      //字典id
    private String pid;     //父id
    private String type;    //字典类型id
    private String name;    //名称

}
