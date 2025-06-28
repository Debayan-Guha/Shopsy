package com.ecom.shopsy.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.shopsy.DTO.Admin;
import com.ecom.shopsy.DTO.Customer;
import com.ecom.shopsy.DTO.Seller;
import com.ecom.shopsy.Repository.AdminRepo;
import com.ecom.shopsy.Service.AdminService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@RestController
@RequestMapping("/shopsy/admin")
public class AdminController {

    @Autowired
    private AdminService as;

    @PostMapping("/register")
    public String adminRegister(@RequestBody @Valid Admin admin) {
        //TODO: process POST request
        
        return as.adminRegister(admin);
    }
    @PutMapping("/updateAdmin-{id}")
    public String postMethodName(@RequestBody @Valid Admin admin,@PathVariable String id) {
        
        return as.updateAdmin(admin,id);
    }
    @PutMapping("/updateCustomer-{id}")
    public String updateCustomer(@RequestBody Set<String> roles,@PathVariable String id) {
        
        return as.updateCustomerRole(roles,id);
    }
    @PutMapping("/updateSeller-{id}")
    public String updateSeller(@RequestBody Set<String> roles,@PathVariable String id) {
        
        return as.updateSellerRole(roles,id);
    }
    
    @GetMapping("/allCustomer")
    public List<Customer> allCustomers() {
        return as.allCustomer();
    }
    @GetMapping("/allSeller")
    public List<Seller> allSellers() {
        return as.allSeller();
    }
    @GetMapping("/allAdmin")
    public List<Admin> allAdmin() {
        return as.allAdmin();
    }
        
}
