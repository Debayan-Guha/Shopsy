package com.ecom.shopsy.Entity;

import com.ecom.shopsy.Enum.Roles;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Roles")
public class RolesEntity {
    
    @Id
   private int id;

    @Enumerated(EnumType.STRING)
   private Roles user_roles;
}
