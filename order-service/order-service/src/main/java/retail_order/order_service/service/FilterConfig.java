package retail_order.order_service.service;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

   @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(JwtService jwtService) {

        FilterRegistrationBean<JwtFilter> reg = new FilterRegistrationBean<>();

        // ✅ Manually create JwtFilter
        reg.setFilter(new JwtFilter(jwtService));

        reg.addUrlPatterns("/*");

        return reg;
    }
}
