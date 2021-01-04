package com.mao.ky.config.valid;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * spring validation
 * 配置快速模式
 * @author : create by zongx at 2020/11/18 10:50
 */
@Configuration
public class ValidationConfiguration {

    public static final String FAST_MODE = "hibernate.validator.fail_fast";

    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                //.messageInterpolator(messageInterpolator())
                .addProperty(FAST_MODE, "true")
                .buildValidatorFactory()
                .getValidator();
    }

}
