package com.example.mvcstart.config;


import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;

/**
 * @author : KaelvihN
 * @date : 2023/9/6 10:09
 */


@Configuration
@ComponentScan("com.example.mvcstart.controller")
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties({WebMvcProperties.class, ServerProperties.class})
public class WebConfig {
    /**
     * 内嵌Web容器工厂
     */
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(ServerProperties serverProperties) {
        return new TomcatServletWebServerFactory(serverProperties.getPort());
    }

    /**
     * 创建DispatcherServlet
     */
    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    /**
     * 注册DispatcherServlet，MVC的入口
     *
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(
            DispatcherServlet dispatcherServlet, WebMvcProperties webMvcProperties) {
        /**
         * 第一个参数为需要注册的DispatcherServlet
         * 第二个参数是请求路径
         *  / 是纸一个请求没有其他路径映射就会与其匹配
         */

        DispatcherServletRegistrationBean registrationBean =
                new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        /**
         * 在Tomcat启动时就初始化(默认为-1，即不在启动时初始化)
         * 设置优先级，值越小优先级越高
         */
        registrationBean.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
        return registrationBean;
    }

    /**
     * 手动创建RequestMappingHandlerMapping，并加入到Spring容器中
     */
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    /**
     * 手动创建RequestMappingHandlerMapping，并加入到Spring容器中
     * 用于调用Controller的方法
     */
    @Bean
    public MyRequestMappingHandleAdapter myRequestMappingHandleAdapter() {
//        TokenArgumentResolver tokenArgumentResolver = new TokenArgumentResolver();
        YmlReturnValueHandler ymlReturnValueHandler = new YmlReturnValueHandler();
        MyRequestMappingHandleAdapter handleAdapter = new MyRequestMappingHandleAdapter();
//        handleAdapter.setArgumentResolvers(List.of(tokenArgumentResolver));
        handleAdapter.setReturnValueHandlers(List.of(ymlReturnValueHandler));
        return handleAdapter;
    }

}
