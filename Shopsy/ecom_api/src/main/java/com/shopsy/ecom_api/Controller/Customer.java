package com.shopsy.ecom_api.Controller;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsy.ecom_api.DTO.Buy_dto;
import com.shopsy.ecom_api.DTO.Customer_dto;
import com.shopsy.ecom_api.DTO.OrderHistory_dto;
import com.shopsy.ecom_api.DTO.Product_dto;
import com.shopsy.ecom_api.Service.CustomerService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/shopsy/customer")
public class Customer {
    

    //Making the CustomerService cs field private in constructor injection is a best practice in Java. It ensures that the cs reference can only be accessed and modified within the class itself. This helps maintain the integrity of your object's state and prevents unintended changes from other parts of your code.   
    private CustomerService cs;
    Customer(CustomerService cs)
    {
        this.cs=cs;
    }

    //controller methods in spring shoould be public .spring needs to be able to access them to handle web requests.if they are private spring wouldnot able to find them.Making your controller methods private or protected will not cause a compilation error. The code will compile fine. However, you will encounter a runtime error when Spring tries to process a request that should be handled by that method. Spring's request mapping mechanism relies on being able to access public methods. It won't be able to find and invoke a private or protected method, resulting in an error like a 404 Not Found or a more specific Spring MVC exception.
    

    //Customer REGISTRATION 
    @PostMapping("/register")
    public String register(@RequestBody @Valid Customer_dto body) {

        return cs.register(body);
    }

    String userId;//just a variable to look good to store the user id from login security
    //Customer Updation 
    @PutMapping("/update")
    public String update(@RequestBody @Valid Customer_dto body) {

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        userId=auth.getName();//fetching user Id form security itself
        return cs.update(userId,body);
    }

    //Customer BUY
    @PostMapping("/buy")
    public String buy(@RequestBody @Valid List<Buy_dto> body) {
        
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        userId=auth.getName();
        System.out.println(auth.toString());
        return cs.buy(userId,body);
    }
    //Customer Order History
    @GetMapping("/history")
    public List<OrderHistory_dto> history() {

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        userId=auth.getName();
        return cs.history(userId);
    }
    
    //Customer SEARCH BY CATEGORY with PAGINATION and SORTING 
    @GetMapping("/category/{category}/sort")
    public List<Product_dto> categoryAndsorting(@PathVariable String category,@RequestParam(defaultValue="low") String sort,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        
        return cs.category(category,sort,page,size);
    }
    //Customer SHOWALL
    @GetMapping("/showAll")
    public List<Product_dto> showAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {

        return cs.showAll(page,size);
    }
    
    
    
    

    
    



}
