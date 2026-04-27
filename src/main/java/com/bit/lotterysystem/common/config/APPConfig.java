package com.bit.lotterysystem.common.config;

import com.bit.lotterysystem.common.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class APPConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    private final List<String> excludes= Arrays.asList(
            "**/*.html",
             "/css/**",
             "/js/**",
             "/pic/**",
             "/*.jpg",
             "/*.png",
             "/favicon.ico",
             "/user/login/**",
            "/user/register",
            "/user/send/verification-code",
            "/picture/upload"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")

                .excludePathPatterns(excludes);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:8081")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("token", "Content-Type") // 明确允许名为 token 的请求头
                .allowCredentials(true);
    }
}
