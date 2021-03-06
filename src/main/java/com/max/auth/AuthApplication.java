package com.max.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
@EnableResourceServer
@EnableAuthorizationServer
public class AuthApplication {

    private static final Logger LOG = LoggerFactory.getLogger(AuthApplication.class);

    /**
     * This endpoint called by protected resources to validate the token and
     * get principal information.
     */
    @RequestMapping(value = {"/user"}, produces = "application/json")
    public Map<String, Object> user(OAuth2Authentication user) {

        LOG.info("/user endpoint called");

        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("user", user.getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getAuthorities()));

        return userInfo;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
