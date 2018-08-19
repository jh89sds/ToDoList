package com.kakao.todolist.config;

import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XssConfig {
    @Bean
    public FilterRegistrationBean httpHeaderSecurityFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        HttpHeaderSecurityFilter httpHeaderSecurityFilter = new HttpHeaderSecurityFilter();
        httpHeaderSecurityFilter.setXssProtectionEnabled(true);
        httpHeaderSecurityFilter.setAntiClickJackingOption("ALLOW-FROM");
        filterRegistrationBean.setFilter(httpHeaderSecurityFilter);
        return filterRegistrationBean;
    }
}
