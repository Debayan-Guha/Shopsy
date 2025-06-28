package com.ecom.shopsy.Entity;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<RolesEntity> roles;

    @OneToOne(fetch = FetchType.LAZY)
    private CustomerEntity customer;

    @OneToOne(fetch = FetchType.LAZY)
    private SellerEntity seller;

    @OneToOne(fetch = FetchType.LAZY)
    private AdminEntity admin;

}
