package org.marino.server.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JWTRenewInterceptor jwtRenewInterceptor;

    public WebMvcConfig(JWTRenewInterceptor jwtRenewInterceptor) {
        this.jwtRenewInterceptor = jwtRenewInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtRenewInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }
}
