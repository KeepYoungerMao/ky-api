package com.mao.ky.util.excel;

import java.lang.annotation.*;

/**
 * Excel字段注解
 * 注明标题和位置
 * 位置起始为1，必须注明，否则不写入Excel
 * @author : create by zongx at 2020/12/22 10:10
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {

    String title() default "";

    int col() default 0;

}
