package com.dss.springboot.service.items.controller;

import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.service.ItemService;
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

    @GetMapping("/items/list")
    public List<Item> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/items/{id}/amount/{amount}")
    public Item getById(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id, amount);
    }
}
