package com.dss.springboot.service.items.model.service.impl;

import com.dss.springboot.service.items.model.domain.Item;
import com.dss.springboot.service.items.model.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public Item findById(Long id) {
        return null;
    }
}
