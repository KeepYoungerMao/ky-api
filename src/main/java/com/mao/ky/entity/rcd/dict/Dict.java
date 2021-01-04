package com.mao.ky.entity.rcd.dict;

import com.mao.ky.config.cache.DictCache;
import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author : create by zongx at 2020/11/25 16:13
 */
@Getter
@Setter
public class Dict {
    private String id;      //字典id
    private String pid;     //父id
    @NotNull
    private String type;    //字典类型id
    @NotNull
    @Length(min = 1, max = 50)
    private String name;    //名称

    public DictDo toDo() {
        DictDo dd = new DictDo();
        dd.setPid(0L);
        DictType dictType = DictCache.getDictType(this.type);
        dd.setType(null == dictType ? null : dictType.getId());
        dd.setName(this.name);
        Sign.sign(dd);
        return dd;
    }
}
