package com.example.demo.repository.impl;

import com.example.demo.model.Item;
import com.example.demo.repository.CustomItemRepository;
import com.mongodb.bulk.BulkWriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomItemRepositoryImpl implements CustomItemRepository {

    MongoTemplate mongoTemplate;

    @Autowired
    public CustomItemRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public int bulkCreateItems(List<Item> itemRequests) {

        BulkOperations bulkInsertion = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, Item.class);
        for (Item itemRequest: itemRequests) {
            bulkInsertion.insert(itemRequest);
        }

        BulkWriteResult bulkWriteResult = bulkInsertion.execute();

        return bulkWriteResult.getInsertedCount();
    }
}
