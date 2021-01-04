package com.mao.ky.config.auth;

import com.mao.ky.service.auth.DefaultAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * 资源服务器
 * @author : create by zongx at 2020/10/15 17:27
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private DefaultAccessDeniedHandler defaultAccessDeniedHandler;

    @Autowired
    public void setDefaultAccessDeniedHandler(DefaultAccessDeniedHandler defaultAccessDeniedHandler) {
        this.defaultAccessDeniedHandler = defaultAccessDeniedHandler;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**")
                .authenticated()
                .and().cors()
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.accessDeniedHandler(defaultAccessDeniedHandler)
                .authenticationEntryPoint(defaultAccessDeniedHandler);
    }

}
