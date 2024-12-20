package org.example.cedc.configuration;

import org.example.cedc.interceptor.APIInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.example.cedc.configuration.SecurityConfig.WHITELISTED_APIS;

/**
 * @author hamza mustafa khan
 * @mailto : hamzamkhan@outlook.com
 */

@Configuration
public class Config implements WebMvcConfigurer {

    private final APIInterceptor interceptor;

    public Config(APIInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .excludePathPatterns(WHITELISTED_APIS);
    }
}
