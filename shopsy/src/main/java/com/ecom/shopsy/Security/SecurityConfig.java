package com.ecom.shopsy.Security;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ecom.shopsy.Service.UserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfig {
    
    @Autowired
    private UserDetailService uds;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http ) throws Exception
    {
        http.csrf(Customizer->Customizer.disable());
        http.authorizeHttpRequests((requests)->requests.requestMatchers("/show-products","/shopsy/customer/register").permitAll().anyRequest().authenticated());
        
        http.httpBasic(Customizer.withDefaults()).formLogin(Customizer.withDefaults());

        
        return http.build();
    }
    @Bean
    AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(uds);
        return provider;

    }
}
