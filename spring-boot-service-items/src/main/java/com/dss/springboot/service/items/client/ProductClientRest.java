package com.dss.springboot.service.items.client;

import com.dss.springboot.service.commons.model.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "service-products")
public interface ProductClientRest {
    @GetMapping("/api/v1/list")
    List<Product> list();

    @GetMapping("/api/v1/{id}")
    Product getById(@PathVariable Long id);

    @PostMapping("/api/v1/create")
    Product save(@RequestBody Product product);

    @PutMapping("/api/v1/update/{id}")
    Product update(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping ("/api/v1/delete/{id}")
    void delete(@PathVariable Long id);
}
