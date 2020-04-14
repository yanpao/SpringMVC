package com.yanpao.config;

import com.yanpao.Controller.UserHandler;
import com.yanpao.Converter.StringToStudent;
import com.yanpao.Converter.StringToTeacher;
import com.yanpao.Service.RunnableDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.util.UrlPathHelper;

import java.lang.reflect.Method;

@Configuration
@EnableWebMvc
@EnableWebSocket
@ComponentScan(basePackages="com.yanpao")
public class WebConfig implements WebMvcConfigurer, WebSocketConfigurer {

    //注册ViewResolver，保证Controller可以保证返回jsp
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        registry.jsp("/WEB-INF/jsp/",".jsp");
    }

    //同上
//    @Bean
//    public ViewResolver viewResolver(){
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//
//        resolver.setPrefix("/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
//        resolver.setExposeContextBeansAsAttributes(true);
//
//        return resolver;
//    }

    //配置Content Negotiation，约束Content-Type的约束
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.mediaType("json", MediaType.APPLICATION_JSON);
//        configurer.mediaType("xml", MediaType.APPLICATION_XML);
//    }

    //注册特殊的指定的controller对应的view
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/testpage").setViewName("test");
//    }

    //注册Resource
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/test/**")
                .addResourceLocations("classpath:/","classpath:/static/","classpath:/public/")
                .setCachePeriod(31556926);
    }

    //注册类型转换
    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        registry.addConverter(new StringToStudent());
        registry.addConverter(new StringToTeacher());
    }

    //手动注册HandleMapping
    @Autowired
    public void setHandlerMapping(RequestMappingHandlerMapping mapping, UserHandler handler) throws NoSuchMethodException {
        RequestMappingInfo info = RequestMappingInfo
                .paths("/handler").methods(RequestMethod.GET).build();
        Method method = UserHandler.class.getMethod("getHandler", Long.class);
        mapping.registerMapping(info, handler, method);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper=new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cors/**")
                .allowedOrigins("https://domain2.com")
                .allowedMethods("PUT", "DELETE")
                .allowedHeaders("header1", "header2", "header3")
                .exposedHeaders("header1", "header2")
                .allowCredentials(true).maxAge(3600);
    }

    @Bean
    public RunnableDemo runnableDemo1()
    {
        return new RunnableDemo("Thread-1");
    }

    @Bean
    public RunnableDemo runnableDemo2()
    {
        return new RunnableDemo("Thread-2");
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/webSocketServer");
        registry.addHandler(myHandler(), "/sockjs/webSocketServer")
                .withSockJS();
    }
    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }

    @Bean
    public HandshakeInterceptor myInterceptor(){return new HandshakeInterceptor();}
}
