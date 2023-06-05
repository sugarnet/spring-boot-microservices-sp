package com.dss.springboot.service.items.model.service.impl;

import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.domain.Product;
import com.dss.springboot.service.items.model.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("itemServiceRestTemplate")
public class ItemServiceRestTemplate implements ItemService {

    private final RestTemplate restTemplate;

    public ItemServiceRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays.asList(
                Optional.ofNullable(
                                restTemplate.getForObject("http://service-products/api/v1/products/list", Product[].class))
                        .orElse(new Product[]{}));

        return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        Product product = restTemplate.getForObject("http://service-products/api/v1/products/{id}", Product.class, params);
        return new Item(product, amount);
    }
}
