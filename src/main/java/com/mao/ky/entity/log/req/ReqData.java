package com.mao.ky.entity.log.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : create by zongx at 2020/12/14 16:15
 */
@Getter
@AllArgsConstructor
public enum ReqData {

    CLIENT(ReqType.SYSTEM, "CLIENT", 1001),
    USER(ReqType.SYSTEM, "USER", 1002),
    ROLE(ReqType.SYSTEM, "ROLE", 1003),
    PERMISSION(ReqType.SYSTEM, "PERMISSION", 1004),

    DICT(ReqType.RECORD, "DICT", 2001),
    COMPANY(ReqType.RECORD, "COMPANY", 2002),
    DEPT(ReqType.RECORD, "DEPARTMENT", 2003),
    STAFF(ReqType.RECORD, "STAFF", 2004);

    private ReqType reqType;
    private String name;
    private int type;

}
