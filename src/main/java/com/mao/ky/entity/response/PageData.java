package com.mao.ky.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 分页封装数据
 * @author : create by zongx at 2020/10/16 15:23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> {
    private Integer page;           //页码
    private Integer row;            //每页条数
    private Long total;          //总数量
    private List<T> data;           //数据列表
}
