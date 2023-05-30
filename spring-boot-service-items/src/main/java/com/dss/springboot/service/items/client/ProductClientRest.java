package com.dss.springboot.service.items.client;

import com.dss.springboot.service.items.model.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-products")
public interface ProductClientRest {
    @GetMapping("/api/v1/products/list")
    public List<Product> list();

    @GetMapping("/api/v1/products/{id}")
    public Product getById(@PathVariable Long id);
}
