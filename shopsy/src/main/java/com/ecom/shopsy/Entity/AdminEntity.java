package com.ecom.shopsy.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="admin")
public class AdminEntity {
    
    @Id
    @Column(unique = true)
    private String id;
    private String name;
    private String password;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "admin")
    private LoginEntity login;
}
