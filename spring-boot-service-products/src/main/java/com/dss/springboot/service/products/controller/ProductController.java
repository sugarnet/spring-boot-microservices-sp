package com.dss.springboot.service.products.controller;

import com.dss.springboot.service.products.model.entity.Product;
import com.dss.springboot.service.products.model.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final Environment environment;

    @Value("${server.port}")
    private Integer port;

    public ProductController(ProductService productService, Environment environment) {
        this.productService = productService;
        this.environment = environment;
    }

    @GetMapping("/list")
    public List<Product> list() {
        return productService.findAll().stream()
                .map(p -> {
                    p.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
                    //p.setPort(port);
                    return p;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        Product product = productService.findById(id);
        product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        //product.setPort(port);
        return product;
    }
}
