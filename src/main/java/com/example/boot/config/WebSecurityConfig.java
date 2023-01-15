package com.example.boot.config;

import com.example.boot.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CredentialService credentialService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                        .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll()
                        .anyRequest().authenticated()
                .and()
                        .formLogin()
                        .loginPage("/login")
                        .permitAll()
                .and()
                        .httpBasic()
                .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                        .csrf()
                        .ignoringAntMatchers("/login", "/logout")
                .and()
                        .logout()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                        .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(credentialService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
