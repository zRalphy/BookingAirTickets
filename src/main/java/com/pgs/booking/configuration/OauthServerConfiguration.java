package com.pgs.booking.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class OauthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenStore tokenStore;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("ClientId")
                .secret(bCryptPasswordEncoder.encode("secret"))
                .accessTokenValiditySeconds(60)
                .scopes("read", "write")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .resourceIds("resource");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) throws Exception {
        endpointsConfigurer
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore);
    }
}
