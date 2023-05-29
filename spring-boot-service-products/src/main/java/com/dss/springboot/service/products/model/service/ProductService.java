package com.dss.springboot.service.products.model.service;

import com.dss.springboot.service.products.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Long id);
}
