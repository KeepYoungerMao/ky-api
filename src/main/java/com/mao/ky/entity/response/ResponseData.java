package com.mao.ky.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 系统数据统一返回体
 * @author : create by zongx at 2020/10/15 17:51
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {

    private int code;           //响应码
    private String msg;         //响应具体码
    private T data;             //响应数据

}
