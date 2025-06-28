package com.ecom.shopsy.Entity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.ecom.shopsy.DTO.Login;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name="Sellers")
public class SellerEntity {

    @Id
    @Column(unique=true)
    protected String id;

    String name;
    
    long ph;
    String shopName;
    String email;    
    String address;
    String password;

    @OneToMany(mappedBy="seller",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<ProductEntity> products=new TreeSet<>();

    @OneToOne(mappedBy="seller",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private LoginEntity login;
    
}
