package com.springboot.app.config;

import com.springboot.app.config.authHandler.LoginSuccessHandler;
import com.springboot.app.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/css/**","/js/**","/images/**","/list").permitAll()
                /*.antMatchers("/see/**").hasAnyRole("USER")
                .antMatchers("/uploads/**").hasAnyRole("USER")
                .antMatchers("/form/**").hasAnyRole("ADMIN")
                .antMatchers("/delete/**").hasAnyRole("ADMIN")
                .antMatchers("/receipt/**").hasAnyRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");


        return http.build();
    }
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /*@Bean
    public UserDetailsService userDetailsService()throws Exception{

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User
                .withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN","USER")
                .build());

        return manager;
    }*/

}
