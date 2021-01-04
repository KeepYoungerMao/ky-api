package com.mao.ky.entity.rcd.dept;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : create by zongx at 2020/12/3 17:24
 */
@Getter
@Setter
public class DeptDo extends Sign {
    private Long id;                //id
    private Long pid;               //pid
    private Long cid;               //company id
    private String name;            //name
    private String code;            //code
    private String phone;           //phone
    private String intro;           //intro
    private Long time;              //成立时间
    private String remark;          //remark

    public DeptCache toCache() {
        DeptCache cache = new DeptCache();
        cache.setId(this.id);
        cache.setPid(this.pid);
        cache.setCid(this.cid);
        cache.setName(this.name);
        cache.setCode(this.code);
        return cache;
    }

    public DeptVo toVo() {
        DeptVo vo = new DeptVo();
        vo.setId(String.valueOf(this.id));
        vo.setPid(String.valueOf(this.pid));
        vo.setCid(String.valueOf(this.cid));
        vo.setName(this.name);
        vo.setCode(this.code);
        vo.setPhone(this.phone);
        vo.setIntro(this.intro);
        vo.setTime(this.time);
        vo.setRemark(this.remark);
        Sign.sign(vo,this);
        return vo;
    }
}
