package com.mao.ky.entity.rcd.dict;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典数据
 * @author : create by zongx at 2020/11/10 15:58
 */
@Getter
@Setter
public class DictDo extends Sign {

    private Long id;            //字典id
    private Long pid;           //父id
    private Long type;          //字典类型id
    private String name;        //名称

    public DictVo toVo() {
        DictVo vo = new DictVo();
        vo.setId(String.valueOf(this.id));
        vo.setPid(String.valueOf(this.pid));
        vo.setType(String.valueOf(this.type));
        vo.setName(this.name);
        Sign.sign(vo,this);
        return vo;
    }

}
