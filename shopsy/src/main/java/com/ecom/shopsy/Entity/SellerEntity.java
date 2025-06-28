package com.ecom.shopsy.Entity;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Seller")
public class SellerEntity {

    @Id
    @Column(unique = true)
    protected String id;

    String name;

    long ph;
    String shopName;
    String email;
    String address;
    String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<ProductEntity> products = new HashSet<>();

    @OneToOne(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LoginEntity login;

}
