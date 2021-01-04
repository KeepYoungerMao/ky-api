package com.mao.ky.entity.rcd.dept;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : create by zongx at 2020/12/3 17:28
 */
@Getter
@Setter
public class DeptCache {
    private long id;
    private long pid;
    private long cid;
    private String name;
    private String code;
}
