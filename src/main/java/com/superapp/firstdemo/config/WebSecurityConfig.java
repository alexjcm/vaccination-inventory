package com.superapp.firstdemo.config;

import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import static com.superapp.firstdemo.util.AppConstants.AUTHORITIES_CLAIM_NAME;
import static com.superapp.firstdemo.util.AppConstants.AUTH_WHITELIST;


/**
 * Enable JWT authentication
 */
@Component
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Anyone can view the Swagger documentation, but calling a secure endpoint will require pressing
     * the Authorize button and providing valid credentials.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(configurer ->
                        configurer
                                .antMatchers(AUTH_WHITELIST)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                // JWT Validation Configuration
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(authenticationConverter());
    }

    /**
     * Our goal is to map user roles into granted authorities.
     * JwtGrantedAuthoritiesConverter expects a JWT claim named scope or scp containing either
     * a space separated list or array of granted authorities.
     * Furthermore, it will add a prefix, SCOPE_ to every granted authority.
     * We need to change this behavior by creating a new instance of JwtAuthenticationConverter.
     *
     * @return
     */
    protected JwtAuthenticationConverter authenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName(AUTHORITIES_CLAIM_NAME);

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }

    /**
     * Password encoder bean.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
