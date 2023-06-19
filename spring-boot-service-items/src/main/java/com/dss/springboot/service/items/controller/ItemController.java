package com.dss.springboot.service.items.controller;

import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.domain.Product;
import com.dss.springboot.service.items.model.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    private final ItemService itemService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public ItemController(@Qualifier("itemServiceFeign") ItemService itemService, CircuitBreakerFactory circuitBreakerFactory) {
        this.itemService = itemService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping("/list")
    public List<Item> findAll(@RequestParam(name = "name", required = false) String name, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println(name);
        System.out.println(token);
        return itemService.findAll();
    }

    @GetMapping("/{id}/amount/{amount}")
    public Item getById(@PathVariable Long id, @PathVariable Integer amount) {
        return circuitBreakerFactory.create("items").run(() -> itemService.findById(id, amount), e -> {
            LOGGER.error("Error Communication", e);
            return alternativeMethod(id, amount);
        });
    }

    public Item alternativeMethod(@PathVariable Long id, @PathVariable Integer amount) {
        Item item = new Item();
        Product product = new Product();

        item.setAmount(amount);
        product.setName("ALTERNATIVE");
        product.setPrice(0.0);
        product.setId(id);
        item.setProduct(product);
        return item;
    }
}
