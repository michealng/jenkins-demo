package com.example.demo.repository;

import com.example.demo.model.Item;

import java.util.List;

public interface CustomItemRepository {
    int bulkCreateItems(List<Item> itemRequests);
}
