package com.max.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private static final int TOKEN_TTL_IN_SEC = 10 * 60 * 60; //  10 hours

    @Autowired
    public OAuth2Config(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("myapp")
                .secret("611191")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("web", "mobile")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .accessTokenValiditySeconds(TOKEN_TTL_IN_SEC);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).
                userDetailsService(userDetailsService);
    }

}
