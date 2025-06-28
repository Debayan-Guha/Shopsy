package com.ecom.shopsy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.shopsy.Entity.LoginEntity;

@Repository
public interface LoginRepo extends JpaRepository<LoginEntity,Integer>{
    
    public LoginEntity getByName(String name);

    public LoginEntity getByCustomer(String id);

    public LoginEntity getBySeller(String id);
}
