package com.dss.springboot.service.items.model.service;

import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.domain.Product;

import java.util.List;

public interface ItemService {
    List<Item> findAll();
    Item findById(Long id, Integer amount);
    Product save(Product product);
    Product update(Product product, Long id);
    void delete(Long id);
}
