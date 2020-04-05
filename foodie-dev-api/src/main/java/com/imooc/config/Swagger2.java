package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger2
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/26 13:53
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    //配置 swagger2 核心配置
    //    http://localhost:8088/swagger-ui.html     原路径
    //    http://localhost:8088/doc.html
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2) //指定api 类型 用于定义汇总信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.imooc.controller"))   // 指定controller包
                .paths(PathSelectors.any())         // 所有controller
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("常学奕 天天吃货 电商平台接口 API ") //文档页标题
                .contact(new Contact("changxueyi",
                        "https://www.changxueyi.com",
                        "ichangxueyi@163.com"))
                .description("专注吃货提供 API 接口文档")
                .version("1.0.1")
                .termsOfServiceUrl("https://www.changxueyi.com")
                .build();

    }
}