package com.github.encryptcase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean
    public Docket docket() {
        // 指定使用 Swagger2 规范
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        // 描述字段支持 Markdown 语法
                        .description("# 加密解密示例工程")
                        .termsOfServiceUrl("https://www.baidu.com")
                        .contact("lxhcaicai")
                        .version("1.0")
                        .build())
//                // 分组名称
                .groupName("用户服务")
                .select()
                // 这里指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.github.encryptcase.web"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
