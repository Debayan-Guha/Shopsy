package com.ecom.shopsy.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecom.shopsy.DTO.Login;
import com.ecom.shopsy.Entity.LoginEntity;
import com.ecom.shopsy.Entity.RolesEntity;
import com.ecom.shopsy.Enum.Roles;
import com.ecom.shopsy.Repository.LoginRepo;

import jakarta.transaction.Transactional;
import lombok.Data;

@Service
@Data
public class UserDetailService implements UserDetailsService {

    @Autowired
    private LoginRepo le;

    String user;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
 
        
        LoginEntity login = le.getByName(username);
        
        
        Login log = new Login();
        log.setName(login.getName());
        log.setPassword(login.getPassword());
        Set<String> r = new HashSet<>();
        System.out.println("name="+login.getName());
        for (RolesEntity iRoles : login.getRoles()) {
            System.out.println("roles="+iRoles.getUserroles());
            r.add(iRoles.getUserroles());
            
        }
        log.setRole(r);

         user=username;

        return log;

    }

}