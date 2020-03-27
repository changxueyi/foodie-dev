package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName CorsConfig
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/26 14:54
 */
@Configuration
public class CorsConfig {
    public CorsConfig(){

    }
    @Bean
    public CorsFilter corsFilter(){
        //1 . 添加cors 配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        //设置是否发送cookie信息
        config.setAllowCredentials(true);
        //设置允许的请求方式 比如GET POST 等请求方式
        config.addAllowedMethod("*");
        //设置允许的Header
        config.addAllowedHeader("*");

        //2 . 为 URL 添加 映射的路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",config);

        //返回重新定义好的 corsSource
        return new CorsFilter(corsSource);
    }
}