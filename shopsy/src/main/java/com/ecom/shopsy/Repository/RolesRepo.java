package com.ecom.shopsy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.shopsy.Entity.RolesEntity;
import com.ecom.shopsy.Enum.Roles;

@Repository
public interface RolesRepo extends JpaRepository<RolesEntity,Integer>{

    
} 