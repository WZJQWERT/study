package com.common.platform.config;

import com.common.platform.sys.auth.entrypoint.JwtAuthenticationEntryPoint;
import com.common.platform.sys.auth.filter.JwtAuthorizationTokenFilter;
import com.common.platform.sys.auth.userdetail.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtUserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    private JwtAuthorizationTokenFilter authenticationTokenFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(jwtUserDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,"/login")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET,"/assets/**","/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .logout().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/kaptcha").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/global/sessionError").permitAll()
                .antMatchers("/system/preview/*").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/global/error").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().sameOrigin().cacheControl();

    }
}
