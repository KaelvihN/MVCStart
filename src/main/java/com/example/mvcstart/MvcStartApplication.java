package com.example.mvcstart;

import com.example.mvcstart.config.MyRequestMappingHandleAdapter;
import com.example.mvcstart.config.WebConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.Map;


@Slf4j
public class MvcStartApplication {
    public static void main(String[] args) throws Exception {
        test5();
    }

    public static void test0() {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    public static void test1() throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        //获取RequestMappingHandlerMapping(解析 @RequestMapping 以及派生注解，在初始化时生成路径与控制器方法的映射关系)
        RequestMappingHandlerMapping handlerMapping =
                context.getBean(RequestMappingHandlerMapping.class);
        /**
         * 获取映射结果
         * key => 方法 ， 请求类型
         * value => 方法所在的全限定类名 ， 方法名 ， 参数
         */
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> {
            System.out.println("k = " + k + ",v=" + v);
        });
        // 根据给定请求获取控制器方法，返回处理器执行链
        HandlerExecutionChain chain
                = handlerMapping.getHandler(new MockHttpServletRequest("GET", "/test1"));
        System.out.println("chain = " + chain);
    }

    public static void test3() throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        //获取RequestMappingHandlerMapping(解析 @RequestMapping 以及派生注解，在初始化时生成路径与控制器方法的映射关系)
        RequestMappingHandlerMapping handlerMapping =
                context.getBean(RequestMappingHandlerMapping.class);
        //模拟请求
        MockHttpServletRequest post = new MockHttpServletRequest("POST", "/test2");
        MockHttpServletResponse response = new MockHttpServletResponse();
        post.setParameter("name", "张三");
        // 根据给定请求获取控制器方法，返回处理器执行链
        HandlerExecutionChain chain
                = handlerMapping.getHandler(post);
        System.out.println("chain = " + chain);
        //获取RequestMappingHandleAdapt
        MyRequestMappingHandleAdapter handlerAdapter =
                context.getBean(MyRequestMappingHandleAdapter.class);
        //调用控制器方法
        handlerAdapter.invokeHandlerMethod(post, response, (HandlerMethod) chain.getHandler());
    }

    public static void test4() throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        //获取RequestMappingHandlerMapping(解析 @RequestMapping 以及派生注解，在初始化时生成路径与控制器方法的映射关系)
        RequestMappingHandlerMapping handlerMapping =
                context.getBean(RequestMappingHandlerMapping.class);
        //模拟请求
        MockHttpServletRequest put = new MockHttpServletRequest("PUT", "/test3");
        MockHttpServletResponse response = new MockHttpServletResponse();
        put.addHeader("token", "令牌");
        // 根据给定请求获取控制器方法，返回处理器执行链
        HandlerExecutionChain chain
                = handlerMapping.getHandler(put);
        System.out.println("chain = " + chain);
        //获取RequestMappingHandleAdapt
        MyRequestMappingHandleAdapter handlerAdapter =
                context.getBean(MyRequestMappingHandleAdapter.class);
        //调用控制器方法
        handlerAdapter.invokeHandlerMethod(put, response, (HandlerMethod) chain.getHandler());
    }

    public static void test5() throws Exception {
        AnnotationConfigServletWebServerApplicationContext context =
                new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
        //获取RequestMappingHandlerMapping(解析 @RequestMapping 以及派生注解，在初始化时生成路径与控制器方法的映射关系)
        RequestMappingHandlerMapping handlerMapping
                = context.getBean(RequestMappingHandlerMapping.class);
        MockHttpServletRequest get = new MockHttpServletRequest("GET", "/test4");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HandlerExecutionChain chain = handlerMapping.getHandler(get);
        System.out.println("chain = " + chain);
        MyRequestMappingHandleAdapter handleAdapter =
                context.getBean(MyRequestMappingHandleAdapter.class);
        handleAdapter.invokeHandlerMethod(get, response, (HandlerMethod) chain.getHandler());
        byte[] content = response.getContentAsByteArray();
        System.out.println(new String(content, StandardCharsets.UTF_8));
    }
}
