package com.dss.springboot.service.items.model.service.impl;

import com.dss.springboot.service.commons.model.entity.Product;
import com.dss.springboot.service.items.client.ProductClientRest;
import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("itemServiceFeign")
public class ItemServiceFeign implements ItemService {

    private final ProductClientRest productClientRest;

    public ItemServiceFeign(ProductClientRest productClientRest) {
        this.productClientRest = productClientRest;
    }

    @Override
    public List<Item> findAll() {
        return productClientRest.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        return new Item(productClientRest.getById(id), amount);
    }

    @Override
    public Product save(Product product) {
        return productClientRest.save(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return productClientRest.update(product, id);
    }

    @Override
    public void delete(Long id) {
        productClientRest.delete(id);
    }
}
