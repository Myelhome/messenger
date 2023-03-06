package com.example.boot.config;

import com.example.boot.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private DataSource dataSource;

    @Value("${rememberme.key}")
    private String meKey;

    @Value("${rememberme.seconds}")
    private int meSeconds;

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
                        .rememberMe()
                        .rememberMeServices(rememberMeAuthenticationProvider())
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
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeAuthenticationProvider() {
        PersistentTokenBasedRememberMeServices rememberMe = new PersistentTokenBasedRememberMeServices(meKey, credentialService, jdbcTokenRepository());
        rememberMe.setTokenValiditySeconds(meSeconds);
        return rememberMe;
    }

    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setCreateTableOnStartup(false);
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}
