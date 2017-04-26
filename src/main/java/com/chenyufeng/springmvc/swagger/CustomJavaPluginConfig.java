package com.chenyufeng.springmvc.swagger;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * by chenyufeng on 2017/3/29 .
 */
@Configuration
@EnableWebMvc
@EnableSwagger
public class CustomJavaPluginConfig extends WebMvcConfigurerAdapter {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo()).includePatterns(".*")
                .useDefaultResponseMessages(false)
                //	.pathProvider(new GtPaths())
                .apiVersion("0.1").swaggerGroup("user");

    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("我的RESTful接口平台",
                "提供详细的后台所有Restful接口", "http://blog.csdn.net/chenyufeng1991",
                "yufengcode@gmail.com", "乞力马扎罗的雪-博客", "http://blog.csdn.net/chenyufeng1991");
        return apiInfo;
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    class GtPaths extends SwaggerPathProvider {

        @Override
        protected String applicationPath() {
            return "/restapi";
        }

        @Override
        protected String getDocumentationPath() {
            return "/restapi";
        }
    }
}
