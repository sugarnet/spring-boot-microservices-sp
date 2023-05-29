package com.dss.springboot.service.products.model.dao;

import com.dss.springboot.service.products.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long> {
}
