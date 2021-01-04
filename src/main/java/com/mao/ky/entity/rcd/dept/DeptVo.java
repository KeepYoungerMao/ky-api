package com.mao.ky.entity.rcd.dept;

import com.mao.ky.entity.Sign;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : create by zongx at 2020/12/3 17:26
 */
@Getter
@Setter
public class DeptVo extends Sign {
    private String id;                //id
    private String pid;               //pid
    private String cid;               //company id
    private String name;            //name
    private String code;            //code
    private String phone;           //phone
    private String intro;           //intro
    private Long time;              //成立时间
    private String remark;          //remark
}
