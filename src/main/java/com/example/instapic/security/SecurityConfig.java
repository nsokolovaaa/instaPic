package com.example.instapic.security;

import com.example.instapic.Service.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.security.auth.message.config.AuthConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthericationEntry jwtAuthericationEntry;
    @Autowired
    private CustomService customService;


    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthericationEntry)
                .and()
                .authorizeRequests()
                .antMatchers(SecurityConstant.SIGN_UP_URLS)
                .permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthentificationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
     JwtAuthentificationFilter jwtAuthentificationFilter() {
        return new JwtAuthentificationFilter();
    }

}
