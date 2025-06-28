package com.ecom.shopsy.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.shopsy.DTO.Product;
import com.ecom.shopsy.DTO.Seller;
import com.ecom.shopsy.Service.SellerService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/shopsy/seller")
public class SellerController {

    @Autowired
    private SellerService ss;

    @PostMapping("/register")
    private String register(@RequestBody @Valid Seller seller) {
        return ss.registerSeller(seller);
    }

    @PostMapping("/add-product")
    public String add(@RequestBody @Valid Product product) {

        return ss.addProduct(product);
    }

    @DeleteMapping("/delete-product-{id}")
    public String add(@PathVariable String id) {

        return ss.deleteProduct(id);
    }

    @PutMapping("/update-product-{id}")
    public String putMethodName(@RequestBody @Valid Product product, @PathVariable String id) {

        return ss.updateProduct(product, id);
    }

    @GetMapping("/showAll-product")
    public List<Product> getAll() {
        return ss.getAll();
    }

}
