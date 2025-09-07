package com.shopsy.ecom_api.Controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsy.ecom_api.DTO.Product_dto;
import com.shopsy.ecom_api.DTO.Seller_dto;
import com.shopsy.ecom_api.Service.SellerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/shopsy/seller")
public class Seller {

    private SellerService ss;

    Seller(SellerService ss) {
        this.ss = ss;
    }

    // Seller REGISTRATION
    @PostMapping("/register")
    public String register(@RequestBody @Valid Seller_dto body) {

        return ss.register(body);
    }

    // Seller Updation
    @PutMapping("/update")
    public String update(@RequestBody @Valid Seller_dto body) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ss.update(auth.getName(), body);
    }

    // Seller ADD PRODUCT
    @PostMapping("/addProduct")
    public String buy(@RequestBody @Valid List<Product_dto> body) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ss.addProduct(body, auth.getName());// here name will be id because i will put seller id during login time
                                             // instead of name
    }

    // Seller update PRODUCT
    @PutMapping("/updateProduct/{id}")
    public String updateProduct(@PathVariable String id, @RequestBody @Valid Product_dto body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ss.updateProduct(id, body, auth.getName());
    }

    // Seller SEARCH BY CATEGORY with PAGINATION
    @GetMapping("/category/{category}/sort")
    public List<Product_dto> category(@PathVariable String category, @RequestParam(defaultValue="low") String sort, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ss.category(category,sort, page, size);
    }

    // Seller SEARCH BY NAME with PAGINATION and SORTING
    @GetMapping("/name-{name}")
    public List<Product_dto> searchByName(@PathVariable String name, @RequestParam(defaultValue="low") String sort,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return ss.searchByName(name, sort, page, size);
    }

    // Customer SHOWALL
    @GetMapping("/showAll")
    public List<Product_dto> showAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ss.showAll(page, size);
    }
}
