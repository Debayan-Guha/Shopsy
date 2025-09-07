package com.shopsy.ecom_api.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopsy.ecom_api.Entity.LoginEntity;

@Repository
public interface LoginRepo extends JpaRepository<LoginEntity, Integer> {

    public LoginEntity getByUserId(String userId);

}
