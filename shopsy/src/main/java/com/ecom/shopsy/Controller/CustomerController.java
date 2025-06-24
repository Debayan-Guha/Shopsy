package com.ecom.shopsy.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.shopsy.DTO.Customer;
import com.ecom.shopsy.DTO.Order;
import com.ecom.shopsy.Service.CustomerService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/shopsy/customer")
public class CustomerController {

    @Autowired
    CustomerService cs;

    @PostMapping("/register")
    public String registerCustomer(@RequestBody @Valid Customer customer) {
        return cs.registerCustomer(customer);
    }
    @PostMapping("/order")
    public String buyProduct(@RequestBody List<Order> order) {
        
        return cs.buyList(order);
    }
    @GetMapping("/history")
    public List<?> orderHistory() {
        return cs.orderHistory();
    }
    
    
    
    
}
