package com.mao.ky.util.pdf;

import java.lang.annotation.*;

/**
 * Pdf 插入表格时对字段的注解
 * 位置起始为1，必须注明，否则不写入Excel
 * 单元格占比为百分制，所有注解的字段的总数之和需为100，请自行分配
 * 超过100一定会出现异常，小于100有可能出现不明异常.
 * @author : create by zongx at 2020/12/23 16:22
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PdfColumn {

    String title() default "";

    int col() default 0;

    float percent() default 0F;

}
