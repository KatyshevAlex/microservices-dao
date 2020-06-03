package com.microservices.daoservice.dao.config;


import com.microservices.daoservice.dao.enity.enums.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Autowired
    private CustomBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Value("${security.queries.users}")
    private String usersQuery;

    @Value("${security.queries.roles}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery);
    }

    //Step 2 - configure encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Step 3 - configure http requests by roles
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/dao/find-user").hasAnyRole(RoleType.ROLE_APPLICATION.getThisAndHigherPriorities())
                .antMatchers(HttpMethod.PUT, "/dao/update-user").hasAnyRole(RoleType.ROLE_APPLICATION.getThisAndHigherPriorities())
                .and()
                .formLogin()
                .defaultSuccessUrl("/logged-in",true)
                .failureUrl("/login-error")
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf().disable();
    }
}
