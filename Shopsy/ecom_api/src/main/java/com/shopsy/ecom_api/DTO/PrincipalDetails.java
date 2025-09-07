package com.shopsy.ecom_api.DTO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

    private String email,password;
    private Set<String> role;
    public PrincipalDetails(String email,String password,Set<String> role)
    {
        this.email=email;
        this.password=password;
        this.role=role;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        for(String i:role)
        {
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(i);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    
}
