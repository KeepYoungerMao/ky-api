package com.mao.ky.entity.log.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 日志请求参数包装类
 * @author : create by zongx at 2020/10/27 15:10
 */
@Getter
@Setter
public class LogParam {
    private Integer type;           //请求类型
    private Integer data;           //数据类型
    private String user;            //用户名
    private Long start;             //开始时间
    private Long end;               //结束时间
    private Integer page;           //页码，起始1，默认1
    private Integer pre;            //本来叫start的，start已经有了。= 0 ||（page - 1） * size
    private Integer size;           //条数，默认20
}
