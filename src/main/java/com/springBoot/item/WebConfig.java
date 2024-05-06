package com.springBoot.item;

import com.springBoot.item.argumentresolver.LoginMemberArgumentResolver;
import com.springBoot.item.interceptor.LogInterceptor;
import com.springBoot.item.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // LoginArgumentResolver 를 이용한 로그인 인증 체크
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    // 스프링 인터셉터 를 이용한 로그인 인증 체크
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //excludePathPatterns 제외되는 경로

        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns( "/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error" );
    }
}