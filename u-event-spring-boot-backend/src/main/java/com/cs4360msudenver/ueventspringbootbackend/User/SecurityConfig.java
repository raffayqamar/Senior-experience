package com.cs4360msudenver.ueventspringbootbackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable() // Disable CSRF for simplicity (you should enable it in production)
                .authorizeRequests()
                .antMatchers("/api/users/**").permitAll() // Allow access to registration and login
                .anyRequest().permitAll()
                .and()
                .httpBasic(); // Use Basic Authentication

        // Configure CORS if needed
         http.cors();

        // Add a custom login page if desired
        // .formLogin()
        //     .loginPage("/login")
        //     .permitAll()
        //     .and()

        // Add a custom logout endpoint if needed
        // .logout()
        //     .logoutUrl("/logout")
        //     .logoutSuccessUrl("/login?logout")
        //     .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS Configuration if not using Spring Boot auto-configuration
     @Bean
     public CorsFilter corsFilter() {
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         CorsConfiguration config = new CorsConfiguration();
         config.addAllowedOrigin("*");
         config.addAllowedMethod("*");
         config.addAllowedHeader("*");
         source.registerCorsConfiguration("/**", config);
         return new CorsFilter(source);
     }
}
