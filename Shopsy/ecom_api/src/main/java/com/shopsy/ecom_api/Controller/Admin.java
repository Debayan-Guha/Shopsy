package com.shopsy.ecom_api.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsy.ecom_api.DTO.Admin_dto;
import com.shopsy.ecom_api.DTO.Customer_dto;
import com.shopsy.ecom_api.DTO.Seller_dto;
import com.shopsy.ecom_api.Service.AdminService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/shopsy/admin")
public class Admin {

    @Autowired
    private AdminService as;

    @PostMapping("/register")
    public String adminRegister(@RequestBody @Valid Admin_dto admin) {
                
        return as.adminRegister(admin);
    }
    @PutMapping("/updateAdmin/{id}")
    public String postMethodName(@RequestBody @Valid Admin admin,@PathVariable String id) {
        
        return as.updateAdmin(admin,id);
    }
    @PutMapping("/updateUser/{id}")
    public String updateUserRole(@RequestBody Set<String> roles,@PathVariable String id) {
        
        return as.updateUserRole(roles,id);
    }
    
    @GetMapping("/allCustomer")
    public List<Customer_dto> allCustomers() {
        return as.allCustomer();
    }
    @GetMapping("/allSeller")
    public List<Seller_dto> allSellers() {
        return as.allSeller();
    }
    @GetMapping("/allAdmin")
    public List<Admin_dto> allAdmin() {
        return as.allAdmin();
    }
    
}
