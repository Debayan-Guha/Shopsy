package com.ecom.shopsy.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.shopsy.DTO.Product;
import com.ecom.shopsy.DTO.Seller;
import com.ecom.shopsy.Entity.LoginEntity;
import com.ecom.shopsy.Entity.ProductEntity;
import com.ecom.shopsy.Entity.RolesEntity;
import com.ecom.shopsy.Entity.SellerEntity;
import com.ecom.shopsy.Enum.Roles;
import com.ecom.shopsy.Repository.LoginRepo;
import com.ecom.shopsy.Repository.ProductRepo;
import com.ecom.shopsy.Repository.RolesRepo;
import com.ecom.shopsy.Repository.SellerRepo;

import jakarta.transaction.Transactional;

@Service
public class SellerService {

    @Autowired
    private SellerRepo sr;
    @Autowired
    private ProductRepo pr;
    @Autowired
    private UserDetailService uds;
    @Autowired
    private RolesRepo rr;
    @Autowired
    private LoginRepo lr;

    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Transactional
    public String registerSeller(Seller seller) {

        SellerEntity se = new SellerEntity();
        se.setId(seller_generateId());
        se.setName(seller.getName());
        se.setPh(seller.getPh());
        se.setShopName(seller.getShopName());
        se.setEmail(seller.getEmail());
        se.setAddress(seller.getAddress());
        se.setPassword(encoder().encode(seller.getPassword()));

        LoginEntity log = new LoginEntity();
        log.setName(se.getName());
        log.setPassword(se.getPassword());
        log.setSeller(se);

        RolesEntity role = rr.getByuserroles(Roles.ROLE_SELLER.toString());
        Set<RolesEntity> re = new HashSet<>();
        re.add(role);
        log.setRoles(re);
        se.setLogin(log);
        sr.save(se);
        return "Created Successfully";

    }

    @Transactional
    public String addProduct(Product product) {

        ProductEntity p = new ProductEntity();
        p.setId(product_generateId());
        p.setName(product.getName());
        p.setCategory(product.getCategory());
        p.setColor(product.getColor());
        p.setPrice(product.getPrice());
        p.setStock(product.getStock());
        p.setAvailable(product.getAvailable());

        SellerEntity se = sr.getByName(uds.getUser());
        p.setSeller(se);
        pr.save(p);
        return "Product Saved Successfully";

    }

    public String deleteProduct(String id) {

        pr.deleteById(id);
        return "Delete Successfully";
    }

    public String updateProduct(Product product, String id) {

        ProductEntity p = pr.getById(id);
        p.setName(product.getName());
        p.setCategory(product.getCategory());
        p.setColor(product.getColor());
        p.setPrice(product.getPrice());
        p.setStock(product.getStock());
        p.setAvailable(product.getAvailable());

        pr.save(p);
        return "Product Update Successfully";
    }

    public List<Product> getAll() {
        
        List<ProductEntity> p = pr.findAll();
        List<Product> pro = new ArrayList<>();
        for (ProductEntity i : p) {
            Product product = new Product();
            product.setId(i.getId());
            product.setName(i.getName());
            product.setCategory(i.getCategory());
            product.setColor(i.getColor());
            product.setPrice(i.getPrice());
            product.setStock(i.getStock());
            product.setAvailable(i.getAvailable());

            pro.add(product);
        }

        return pro;
    }

    private String seller_generateId() {
        // Get the count of existing sellers
        long count = sr.count();

        // Format the ID with leading zeros (sell_1001, sell_1002, etc.)
        return String.format("s_%04d", count + 1);

    }

    private String product_generateId() {
        // Get the count of existing sellers
        long count = pr.count();

        // Format the ID with leading zeros (sell_1001, sell_1002, etc.)
        return String.format("p_%04d", count + 1);
    }

}
