package com.dss.springboot.service.products.controller;

import com.dss.springboot.service.products.model.entity.Product;
import com.dss.springboot.service.products.model.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/list")
    public List<Product> list() {
           return productService.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getById(@PathVariable Long id) {
           return productService.findById(id);
    }
}
