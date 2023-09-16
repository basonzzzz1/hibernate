package com.be.config;

import com.be.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    IAccountService accountService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    FilterAuthToken filterAuthToken;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "orderDetail", "orders/**", "api/**", "/api/products**", "/apiAccount/creatAccount**").permitAll()
                .antMatchers("/api/imgProduct").permitAll()
                .antMatchers("/api/product/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/api/categories").permitAll()
                .antMatchers("/orderDetail").permitAll()
                .antMatchers("/orders/**").permitAll()
                .antMatchers("/api/productByCategory/**").permitAll()
                .antMatchers("/api/products/search/**").permitAll()
                .and().authorizeRequests().antMatchers("/user**").hasRole("USER")
                .and().authorizeRequests().antMatchers("/admin**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().csrf().disable();

        http.addFilterBefore(filterAuthToken, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Sửa cấu hình CORS
        http.cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:63343"); // Cho phép nguồn này truy cập
        configuration.addAllowedMethod("*"); // Cho phép tất cả các phương thức HTTP (GET, POST, PUT, DELETE, v.v.)
        configuration.addAllowedHeader("*"); // Cho phép tất cả các tiêu đề

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}