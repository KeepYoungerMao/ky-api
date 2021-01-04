package com.mao.ky.config;

import com.mao.ky.config.http.HttpServletRequestReplacedFilter;
import com.mao.ky.util.MaoFlake;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目需要加载的Bean
 * 除一些特殊的Bean（如验证框架Validation，OAuth2等），其它的Bean都在此进行加载
 * @author : create by zongx at 2020/10/20 15:33
 */
@Configuration
public class ProjectConfiguration {

    /**
     * ID 生成器
     */
    @Bean
    public MaoFlake maoFlake() {
        return new MaoFlake();
    }

    /**
     * 注册过滤器，解决HTTP Servlet Request读取InputStream流后流关闭了，不能再次读取的情况。
     */
    @Bean
    public FilterRegistrationBean<HttpServletRequestReplacedFilter> filterRegistrationBean() {
        FilterRegistrationBean<HttpServletRequestReplacedFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new HttpServletRequestReplacedFilter());
        registration.addUrlPatterns("/*");
        registration.setName("httpServletRequestReplacedFilter");
        registration.setOrder(1);
        return registration;
    }

}
