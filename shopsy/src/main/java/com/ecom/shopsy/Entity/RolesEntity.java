package com.ecom.shopsy.Entity;

import java.util.List;
import java.util.Set;

import com.ecom.shopsy.Enum.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Roles")
public class RolesEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    
    private String userroles;

   @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
   private List<LoginEntity> logins;
}
