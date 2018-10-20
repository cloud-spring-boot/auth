package com.max.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


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
    public Principal user(Principal user) {
        LOG.info("/user endpoint called");

        return user;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
