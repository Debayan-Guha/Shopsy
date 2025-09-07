package com.shopsy.ecom_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopsy.ecom_api.Entity.RoleEntity;

@Repository
public interface RoleRepo extends JpaRepository<RoleEntity,Integer>{


    public RoleEntity getByRoleName(String string);
    
}
