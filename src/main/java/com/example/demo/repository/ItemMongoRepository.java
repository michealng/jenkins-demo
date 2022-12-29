package com.example.demo.repository;

import com.example.demo.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMongoRepository extends MongoRepository<Item, String> {
    @Query("{'name': '?0'}")
    Item findItemByName(String name);

    @Query(value="{'category': '?0'}", fields = "{'name' : 1, 'quantity' : 1}")
    Page<Item> findItemsByCategory(String category, Pageable pageable);

}
