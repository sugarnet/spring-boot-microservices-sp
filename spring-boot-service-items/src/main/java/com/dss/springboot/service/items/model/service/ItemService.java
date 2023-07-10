package com.dss.springboot.service.items.model.service;

import com.dss.springboot.service.commons.model.entity.Product;
import com.dss.springboot.service.items.model.domain.Item;

import java.util.List;

public interface ItemService {
    List<Item> findAll();
    Item findById(Long id, Integer amount);
    Product save(Product product);
    Product update(Product product, Long id);
    void delete(Long id);
}
