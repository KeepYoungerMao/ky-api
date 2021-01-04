package com.mao.ky.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 列表查询统一参数
 * @author : create by zongx at 2020/11/11 17:58
 */
@Getter
@Setter
public class Query {

    private String type;        //page，keyword
    private String keyword;     //关键词
    private Integer page;       //页码
    private Integer size;       //每页条数

    //计算参数
    private Integer start;     //数据库使用查询开始点

}
