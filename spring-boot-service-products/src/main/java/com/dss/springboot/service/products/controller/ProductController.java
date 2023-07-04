package com.dss.springboot.service.products.controller;

import com.dss.springboot.service.products.model.entity.Product;
import com.dss.springboot.service.products.model.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
    public Product getById(@PathVariable Long id) throws InterruptedException {

        if (id.equals(10L)) {
            throw new IllegalStateException("Product Not Found!");
        }

        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(5);
        }

        Product product = productService.findById(id);
        product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        //product.setPort(port);

        return product;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@RequestBody Product product, @PathVariable Long id) {
        Product productDb = productService.findById(id);
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        return productService.save(productDb);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
