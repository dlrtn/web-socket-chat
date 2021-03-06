package com.dlrtn.websocket.chat.config.security;

import com.dlrtn.websocket.chat.business.user.model.domain.UserAuthRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.dlrtn.websocket.chat.common.model.PagePathConstants.LOGIN;
import static com.dlrtn.websocket.chat.common.model.PagePathConstants.SIGNUP;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", LOGIN, SIGNUP, "/stomp/chat").permitAll()
                .mvcMatchers("/admin").hasRole(UserAuthRole.ADMIN.name())
                .anyRequest().permitAll();

        http.formLogin()
                .disable()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("dlrtn").password("{noop}1234").roles("USER")
                .and().withUser("admin").password("{noop}1234").roles("ADMIN");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
