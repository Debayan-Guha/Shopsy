package com.ecom.shopsy.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.shopsy.Entity.RolesEntity;
import com.ecom.shopsy.Enum.Roles;
import com.ecom.shopsy.Repository.RolesRepo;

import jakarta.annotation.PostConstruct;
@Service
public class Role_Initialize {
    
    @Autowired
    private RolesRepo rr;

    
    @PostConstruct
    void Initialize()
    {
         
        if(rr.count()==0)
        {
            RolesEntity a=new RolesEntity();
            RolesEntity c=new RolesEntity();
            RolesEntity s=new RolesEntity();

            a.setUserroles("ROLE_"+Roles.ADMIN.toString());
            c.setUserroles("ROLE_"+Roles.CUSTOMER.toString());
            s.setUserroles("ROLE_"+Roles.SELLER.toString());

            List<RolesEntity> list=new ArrayList<>(); 
            list.add(a);
            list.add(c);
            list.add(s);

            rr.saveAll(list);
        }
    }
   
}


