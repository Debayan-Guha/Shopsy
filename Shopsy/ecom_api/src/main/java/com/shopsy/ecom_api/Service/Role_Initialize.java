package com.shopsy.ecom_api.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopsy.ecom_api.Entity.RoleEntity;
import com.shopsy.ecom_api.Enum.Roles;
import com.shopsy.ecom_api.Repository.RoleRepo;

import jakarta.annotation.PostConstruct;

@Service
public class Role_Initialize {

    @Autowired
    private RoleRepo rr;

    @PostConstruct
    void initialize()
    {
        if(rr.count()==3)
        {
            return;
        }
        RoleEntity r1=new RoleEntity();
        RoleEntity r2=new RoleEntity();
        RoleEntity r3=new RoleEntity();

        r1.setRoleName(Roles.ROLE_ADMIN.toString());
        r2.setRoleName(Roles.ROLE_CUSTOMER.toString());
        r3.setRoleName(Roles.ROLE_SELLER.toString());

        Set<RoleEntity> role=new HashSet<>();
        role.add(r1);
        role.add(r2);
        role.add(r3);

        rr.saveAll(role);
    }
    
}
