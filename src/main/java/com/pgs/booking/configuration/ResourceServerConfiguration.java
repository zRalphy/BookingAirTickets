package com.pgs.booking.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static com.pgs.booking.configuration.WebSecurityConfiguration.WHITE_LIST;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests().antMatchers(WHITE_LIST).permitAll()
            .and()
            .authorizeRequests().antMatchers("/api/**").authenticated();
    }
}
