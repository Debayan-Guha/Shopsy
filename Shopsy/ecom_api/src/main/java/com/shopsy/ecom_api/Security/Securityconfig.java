package com.shopsy.ecom_api.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.shopsy.ecom_api.Enum.Roles;
import com.shopsy.ecom_api.Service.UserDetailService;

@Configuration
@EnableWebSecurity
public class Securityconfig {
    
    @Autowired
    private UserDetailService uds;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(Customizer -> Customizer.disable());
        //authorization
        http.authorizeHttpRequests((request)->request.requestMatchers("/shopsy/customer/register","/shopsy/seller/register","/shopsy/admin/register").permitAll().requestMatchers("/shopsy/customer/**").hasRole("CUSTOMER").requestMatchers("/shopsy/seller/**").hasRole("SELLER").requestMatchers("/shopsy/admin/**").hasRole("ADMIN").anyRequest().authenticated());//we have to give full url to protect

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }
    @Bean
    AuthenticationProvider authenticationProvider()
    {
        System.out.println("Security Testing");
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(uds);

        return provider;
    }
}
