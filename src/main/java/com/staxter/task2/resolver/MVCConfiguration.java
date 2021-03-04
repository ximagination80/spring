package com.staxter.task2.resolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import java.util.List;

@Configuration
@EnableWebMvc
public class MVCConfiguration implements WebApplicationInitializer, WebMvcConfigurer {

    private final UserResolver userResolver;

    public MVCConfiguration(UserResolver userResolver) {
        this.userResolver = userResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userResolver);
    }

    @Override
    public void onStartup(ServletContext servletContext) {
    }
}
