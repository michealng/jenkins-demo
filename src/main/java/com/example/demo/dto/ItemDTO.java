package com.example.demo.dto;

import com.example.demo.model.BaseItem;
import com.example.demo.model.Item;

public class ItemDTO extends BaseItem {

    public ItemDTO(Item item) {
        this(item.getName(), item.getCategory(), item.getQuantity());
    }

    public ItemDTO(String name, String category, int quantity) {
        this.setName(name);
        this.setCategory(category);
        this.setQuantity(quantity);
    }
}
