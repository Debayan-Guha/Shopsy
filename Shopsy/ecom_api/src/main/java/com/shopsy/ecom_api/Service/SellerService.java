package com.shopsy.ecom_api.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopsy.ecom_api.DTO.Product_dto;
import com.shopsy.ecom_api.DTO.Seller_dto;
import com.shopsy.ecom_api.Entity.LoginEntity;
import com.shopsy.ecom_api.Entity.ProductEntity;
import com.shopsy.ecom_api.Entity.RoleEntity;
import com.shopsy.ecom_api.Entity.SellerEntity;
import com.shopsy.ecom_api.Enum.Roles;
import com.shopsy.ecom_api.Exception.ProductException;
import com.shopsy.ecom_api.Repository.LoginRepo;
import com.shopsy.ecom_api.Repository.ProductRepo;
import com.shopsy.ecom_api.Repository.RoleRepo;
import com.shopsy.ecom_api.Repository.SellerRepo;
import com.shopsy.ecom_api.Service.Interface.interSellerService;

import jakarta.transaction.Transactional;

@Service
public class SellerService implements interSellerService {

    @Autowired
    private SellerRepo sr;
    @Autowired
    private ProductRepo pr;
    @Autowired
    private RoleRepo rr;
    @Autowired
    private LoginRepo lr;

    private ModelMapper mapper=new ModelMapper();

    // Product id generate
    private String generateProductId() {
        long count = pr.count();

        return String.format("p_%04d", count + 1);
    }

    // Seller id generate
    private String generateSellertId() {
        long count = sr.count();

        return String.format("s_%04d", count + 1);
    }

    // creating instance of PasswordEncoder
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Transactional
    public String register(Seller_dto body) {

        // convert DTO to Entity
        SellerEntity sel_entity = mapper.map(body, SellerEntity.class);
        sel_entity.setId(generateSellertId());
        sel_entity.setPassword(bCryptPasswordEncoder().encode(body.getPassword()));
        sel_entity.setDate(LocalDateTime.now());

        LoginEntity login=new LoginEntity();
        login.setUserId(sel_entity.getId());
        login.setPassword(sel_entity.getPassword());
        //fetching the role obj from db and set it in login db
        Set<RoleEntity> role=new HashSet<>();
        RoleEntity roleEntity=rr.getByRoleName(Roles.ROLE_SELLER.toString());
        role.add(roleEntity);
        login.setRole(role);

        login.setSellerId(sel_entity);

        sel_entity.setLog(login);
        sr.save(sel_entity);

        return "Successfully Register";

    }

    @Override
    public String update(String id, Seller_dto body) {
    // fetch existing entity
    SellerEntity sel_entity = sr.getById(id);
    // map dto → existing entity (updates matching fields)
    mapper.map(body, sel_entity);
    // manually handle password (encode before save)
    sel_entity.setPassword(bCryptPasswordEncoder().encode(body.getPassword()));

    //fetch login table detail to reset password
    LoginEntity loginEntity=lr.getByUserId(id);
    loginEntity.setPassword(sel_entity.getPassword());

    sr.save(sel_entity);

    return "Successfully updated";
}


    @Override
    @CacheEvict(value = {"Product_showAll", "Product_category" , "Product_searchByName","Customer_Product_showAll","Customer_Product_category"}, allEntries = true)
    public String addProduct(List<Product_dto> body, String id) {

        // Fetch seller details from db to assign to product as fk
        SellerEntity seller_obj = sr.getById(id);
        // Convert DTO to Entity
        for (Product_dto i : body) {
            ProductEntity pro_Entity = new ProductEntity();
            mapper.map(i, pro_Entity);
            pro_Entity.setId(generateProductId());
            pro_Entity.setSeller(seller_obj);

            pr.save(pro_Entity);
        }
        return "Product added Successfully";
    }

    @Override
    @CacheEvict(value = {"Product_showAll", "Product_category" , "Product_searchByName","Customer_Product_showAll","Customer_Product_category"}, allEntries = true)
    public String updateProduct(String product_id, Product_dto body, String seller_id) {

        // fetch product from db
        ProductEntity productEntity = pr.getById(product_id);
        // checking this product has seller id correct or not
        if (!seller_id.equals(productEntity.getSeller().getId())) {
            throw new ProductException("Not a correct Product Id");
        }
        mapper.map(body, productEntity);
        productEntity.setId(product_id);
        pr.save(productEntity);
        return "Successfully Updated";
    }

    // import "page" from data domain
    @Override
    @Cacheable(value = "Product_category",key ="#category + #sort + '-' + #size")
    public List<Product_dto> category(String category,String sort, int page, int size) {
        if(sort.equalsIgnoreCase("low")) {
            sort = "ASC";
        } else {
            sort = "DESC";
        }
        Sort s=Sort.by(Sort.Direction.fromString(sort),"price");
        Pageable pageable = PageRequest.of(page, size,s);
        Page<ProductEntity> products_page=pr.findAll(pageable);
        List<Product_dto> products_list=new ArrayList<>();
        for(ProductEntity i:products_page)
        {
            Product_dto product=mapper.map(i,Product_dto.class);
            
            products_list.add(product); 
        }
        return products_list;
    }

    // search product by name
    @Override
    @Cacheable(value = "Product_searchByName",key ="#name + #sort + #page +'-' + #size")
    public List<Product_dto> searchByName(String name, String sort, int page, int size) {
        if(sort.equalsIgnoreCase("low")) {
            sort = "ASC";
        } else {
            sort = "DESC";
        }
        Sort s = Sort.by(Sort.Direction.fromString(sort), "price");// assume we sort over price
        Pageable pageable = PageRequest.of(page, size, s);

        //fetch product in page form
        Page<ProductEntity> products_page=pr.findByFilterAndSort(name, pageable);
        List<Product_dto> products_list=new ArrayList<>();
        for(ProductEntity i:products_page)
        {
            Product_dto product=mapper.map(i,Product_dto.class);
            
            products_list.add(product);
        }
        return products_list;
    }

    /*it's almost always a good practice to map a Page<Entity> to a Page<DTO> before converting it to JSON. This approach offers several significant benefits, especially in a Spring Boot application.
Why Map Page to DTO?
Serialization Safety: The most crucial reason is to prevent LazyInitializationException errors. When you return a Page<Entity> directly, the serialization library (like Jackson) will try to access lazy-loaded relationships, which often occurs after the database session is closed. Mapping to a DTO ensures that you have control over which related entities are loaded and sent in the JSON response. */
    @Override
    @Cacheable(value = "Product_showAll",key="#page + '-' + #size")
    public List<Product_dto> showAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> products_page=pr.findAll(pageable);
        List<Product_dto> products_list=new ArrayList<>();
        for(ProductEntity i:products_page)
        {
            Product_dto product=mapper.map(i,Product_dto.class);
            
            products_list.add(product);
        }
        return products_list;
    }

}
