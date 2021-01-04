package com.mao.ky.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : create by zongx at 2020/10/15 17:53
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    OK(200,"OK"),
    AUTH(401,"NO_PERMISSION"),
    NO(404,"NOT_FOUND"),
    REFUSE(405,"NOT_ALLOWED"),
    ERR(500,"REQUEST_ERROR"),

    // ====================  以下是细分错误类型，code统一为500，msg使用不同的代号 ============

    //请求时传递的参数错误，查询参数或保存、更新的输入参数错误
    PARAM_ERR(500, "PARAM_ERROR"),
    //数据库数据错误，理应正常的访问却查询不到或查询到错误的数据
    DATABASE_ERR(500, "DATABASE_ERROR"),
    //数据库保存、更新、删除等数据时出错
    DATA_ACCESS_ERR(500, "DATA_ACCESS_ERROR");

    private int code;
    private String msg;

}
