package com.example.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    private String id;
    private String name;
    private String category;
    private int quantity;

    public Item(BaseItem item) {
        this.name = item.getName();
        this.category = item.getCategory();
        this.quantity = item.getQuantity();
    }
}
