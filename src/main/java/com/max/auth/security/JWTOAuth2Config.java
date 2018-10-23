package com.max.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {

    private static final int TOKEN_TTL_IN_SEC = 10 * 60 * 60; //  10 hours

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final TokenStore tokenStore;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final TokenEnhancer jwtTokenEnhancer;

    @Autowired
    public JWTOAuth2Config(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                           TokenStore tokenStore, JwtAccessTokenConverter jwtAccessTokenConverter,
                           TokenEnhancer jwtTokenEnhancer) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenStore = tokenStore;
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
        this.jwtTokenEnhancer = jwtTokenEnhancer;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTokenEnhancer, jwtAccessTokenConverter));

        endpoints.tokenStore(tokenStore)                             //JWT
                .accessTokenConverter(jwtAccessTokenConverter)       //JWT
                .tokenEnhancer(tokenEnhancerChain)                   //JWT
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        final InMemoryClientDetailsServiceBuilder clientDetailsBuilder = clients.inMemory();

        clientDetailsBuilder
                .withClient("myweb")
                .secret("611191")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("web")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .accessTokenValiditySeconds(TOKEN_TTL_IN_SEC);

        clientDetailsBuilder
                .withClient("mymobile")
                .secret("611191")
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("mobile")
                .authorities("ROLE_USER")
                .accessTokenValiditySeconds(TOKEN_TTL_IN_SEC);
    }
}
