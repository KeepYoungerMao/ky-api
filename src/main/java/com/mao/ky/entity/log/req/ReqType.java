package com.mao.ky.entity.log.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : create by zongx at 2020/12/14 16:15
 */
@Getter
@AllArgsConstructor
public enum ReqType {

    SYSTEM("SYSTEM", 1),

    RECORD("RECORD", 2);

    private String name;
    private int type;

}
