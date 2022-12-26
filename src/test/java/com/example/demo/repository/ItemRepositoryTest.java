package com.example.demo.repository;

import com.example.demo.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@DataMongoTest
//@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@DirtiesContext
public class ItemRepositoryTest {

    @Autowired
    private ItemMongoRepository itemMongoRepository;


    @Test
    public void findItemByName_returnTrue() {
        Item item = new Item("id", "name", "category", 1);
        itemMongoRepository.save(item);

        Item result = itemMongoRepository.findItemByName("name");

        then("name").as("Validate name").isEqualTo(result.getName());
    }

    @Test
    public void findItemsByCategory_returnTrue() {
        Item item = new Item("id", "name", "category", 1);
        itemMongoRepository.save(item);
        Pageable pageRequest = PageRequest.of(0, 5);

        Page<Item> itemPage = itemMongoRepository.findItemsByCategory("category", pageRequest);
        List<Item> pageList = itemPage.getContent();

        pageList.forEach(item1 -> {
            then("name").as("Validate category").isEqualTo(item1.getName());
        });

    }

}
