package com.mao.ky.config;

import com.mao.ky.config.param.GlobalConfig;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 * @author : create by zongx at 2020/10/15 16:51
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    private GlobalConfig globalConfig;

    @Autowired
    public void setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
    }

    /**
     * 添加资源映射
     * @param registry 注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String docs = globalConfig.getResource().getDocs();
        if (SU.isEmpty(docs) || !(docs.startsWith("classpath:") || docs.startsWith("file:")))
            registryError();
        String images = globalConfig.getResource().getImages();
        images = "file:" + images;
        //文档资源映射
        registry.addResourceHandler("/docs/**").addResourceLocations(docs);
        //图片映射
        registry.addResourceHandler("/images/**").addResourceLocations(images);
    }


    /**
     * 注册失败，抛出异常
     */
    private void registryError() {
        throw new RuntimeException("启动失败：参数" + "dy.resource.docs" + "配置错误。请以class path:或file:开头并配置正确的路径。");
    }

}
