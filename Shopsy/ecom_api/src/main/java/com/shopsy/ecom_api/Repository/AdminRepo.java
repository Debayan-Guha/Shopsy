package com.shopsy.ecom_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopsy.ecom_api.Entity.AdminEntity;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity,String>{
    
}
