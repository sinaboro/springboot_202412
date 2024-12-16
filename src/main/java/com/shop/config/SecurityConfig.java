package com.shop.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       log.info("--------------------filterChain----------------------------");

       http
               .formLogin(config->
                        config.loginPage("/members/login")
                       .defaultSuccessUrl("/")
                       .usernameParameter("email")  //로그인화면에서 name=username이면 생락가능  --> name=email
                       .failureUrl("/members/login/error")
               )
               .logout(logout->
                       logout.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                       .logoutSuccessUrl("/")
               );

//       http.csrf().disable();
       http
               .csrf(config->config.disable());

       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
