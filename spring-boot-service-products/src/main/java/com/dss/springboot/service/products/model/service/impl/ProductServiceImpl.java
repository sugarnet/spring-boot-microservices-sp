package com.dss.springboot.service.products.model.service.impl;

import com.dss.springboot.service.products.model.dao.ProductDao;
import com.dss.springboot.service.products.model.entity.Product;
import com.dss.springboot.service.products.model.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productDao.deleteById(id);
    }
}
