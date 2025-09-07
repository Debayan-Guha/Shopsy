package com.shopsy.ecom_api.Entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class AdminEntity {

    @Id
    private String id;
    private String name;
    private String password;

    @OneToOne(mappedBy = "adminId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private LoginEntity login;
}
