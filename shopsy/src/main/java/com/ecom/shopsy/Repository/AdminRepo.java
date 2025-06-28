package com.ecom.shopsy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.shopsy.Entity.AdminEntity;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity,String>{
    
}
