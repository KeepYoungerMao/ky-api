package com.mao.ky.entity.log.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 请求日志数据，简要列表
 * 用于前端数据展现
 * @author : create by zongx at 2020/10/27 15:44
 */
@Getter
@Setter
public class ReqLogVo {

    private String id;              //主键
    private String type;            //请求类型
    private String data;            //数据类型
    private String client;          //客户端
    private String user;            //用户
    private String ip;              //ip
    private String uri;             //uri
    private String method;          //请求方法
    private Map<String, String> params;    //请求表单参数
    private String body;            //请求body参数
    private Integer status;         //结果状态
    private String result;          //错误结果
    private String date;            //请求时间

}
