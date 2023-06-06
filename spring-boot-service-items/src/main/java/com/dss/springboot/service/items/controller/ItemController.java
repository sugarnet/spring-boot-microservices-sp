package com.dss.springboot.service.items.controller;

import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.domain.Product;
import com.dss.springboot.service.items.model.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ItemController {
    private final ItemService itemService;

    public ItemController(@Qualifier("itemServiceFeign") ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public List<Item> findAll() {
        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/{id}/amount/{amount}")
    public Item getById(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id, amount);
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
