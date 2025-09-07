package com.shopsy.ecom_api.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopsy.ecom_api.DTO.PrincipalDetails;
import com.shopsy.ecom_api.Entity.LoginEntity;
import com.shopsy.ecom_api.Entity.RoleEntity;
import com.shopsy.ecom_api.Repository.LoginRepo;

import jakarta.transaction.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private LoginRepo lr;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        
        LoginEntity loginEntity=lr.getByUserId(userId);
        if(loginEntity==null)
        {
            throw new UsernameNotFoundException("User Not Found");
        }
        Set<String> role=new HashSet<>();
        for(RoleEntity i:loginEntity.getRole())
        {
            role.add(i.getRoleName());
        }
        PrincipalDetails principal=new PrincipalDetails(loginEntity.getUserId(),loginEntity.getPassword(),role);        
        
        return principal;
    }
    
}
