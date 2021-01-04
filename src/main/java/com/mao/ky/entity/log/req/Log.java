package com.mao.ky.entity.log.req;

import java.lang.annotation.*;

/**
 * @author : create by zongx at 2020/12/14 16:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    ReqData value();

}
