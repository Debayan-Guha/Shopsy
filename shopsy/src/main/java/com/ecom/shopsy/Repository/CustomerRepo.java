package com.ecom.shopsy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.shopsy.Entity.CustomerEntity;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity,String>{

    public CustomerEntity getByName(String username);

    
} 
