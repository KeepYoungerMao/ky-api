package com.mao.ky.entity.log.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 日志类型树结构
 * @author : create by zongx at 2020/10/27 14:47
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogTree {
    private Integer id;
    private String name;
    private List<LogTree> child;
}
