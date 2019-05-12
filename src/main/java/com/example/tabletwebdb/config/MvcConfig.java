package com.example.tabletwebdb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadpath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"/css/**"}).addResourceLocations(new String[]{"classpath:/css/"});
        registry.addResourceHandler(new String[]{"/image/**"}).addResourceLocations(new String[]{"classpath:/image/"});
        registry.addResourceHandler(new String[]{"/script/**"}).addResourceLocations(new String[]{"classpath:/script/"});
        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + uploadpath + "/");
    }

}