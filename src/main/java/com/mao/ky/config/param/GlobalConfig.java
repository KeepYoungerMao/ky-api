package com.mao.ky.config.param;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目参数总配置
 * @author : create by zongx at 2020/10/15 16:52
 */
@Component
@ConfigurationProperties("dy")
@Getter
@Setter
public class GlobalConfig {

    private ResourceConfig resource;        //本地资源参数配置
    private BuildConfig build;              //数据创建参数

}
