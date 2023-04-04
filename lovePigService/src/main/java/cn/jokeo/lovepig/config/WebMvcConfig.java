package cn.jokeo.lovepig.config;

import cn.jokeo.lovepig.security.interceptor.LoginUserInterceptor;
import cn.jokeo.lovepig.security.interceptor.PigLoginInterceptor;
import cn.jokeo.lovepig.security.interceptor.PigRolesInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * springmvc扩展配置类
 *
 * @author joke
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 解决跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true).maxAge(3600);
    }

    /**
     * 查询用户信息注入拦截器
     *
     * @return
     */
    @Bean
    @Order(value = 2147483600)
    public LoginUserInterceptor loginUserInterceptor() {
        return new LoginUserInterceptor();
    }

    @Bean
    @Order(value = 2147483601)
    public PigLoginInterceptor pigLoginInterceptor() {
        return new PigLoginInterceptor();
    }

    @Bean
    @Order(value = 2147483602)
    public PigRolesInterceptor pigRolesInterceptor() {
        return new PigRolesInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginUserInterceptor())
                .excludePathPatterns("/css/**", "/html/**", "/img/**", "/js/**",
                        "*.css", "*.js", "*.html", "*.png", "*.jpg", "/register",
                        "/userLogin/**", "/error")
                .addPathPatterns("/**");
        registry.addInterceptor(pigLoginInterceptor())
                .excludePathPatterns("/css/**", "/html/**", "/img/**", "/js/**",
                        "*.css", "*.js", "*.html", "*.png", "*.jpg", "/register",
                        "/userLogin/**", "/error")
                .addPathPatterns("/**");
        registry.addInterceptor(pigRolesInterceptor())
                .excludePathPatterns("/css/**", "/html/**", "/img/**", "/js/**",
                        "*.css", "*.js", "*.html", "*.png", "*.jpg", "/register",
                        "/userLogin/**", "/error")
                .addPathPatterns("/**");
    }

//    @Bean
//    public Validator hibernateValidator() {
//
//    }

}