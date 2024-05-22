package com.mangazo.ecommerce.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // protect endpoints
        http.authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/api/orders/**")
                                .authenticated()
                                .anyRequest().permitAll())
                .oauth2ResourceServer(oAuth -> oAuth.jwt(Customizer.withDefaults()))
                        .cors(Customizer.withDefaults());

        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        Okta.configureResourceServer401ResponseBody(http);

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
