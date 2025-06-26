package com.ecom.shopsy.DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login implements UserDetails{
    
    private String name;
    private String password;
    private Set<String> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> auth=new ArrayList<>();
        for (String string : role) {
            SimpleGrantedAuthority s=new SimpleGrantedAuthority(string);
            auth.add(s);
        }
        return auth;
    }
    @Override
    public String getUsername() {
        return name;
    }
    
}
