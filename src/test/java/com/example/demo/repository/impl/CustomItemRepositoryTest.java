//package com.example.demo.repository.impl;
//
//import com.example.demo.model.Item;
//import com.example.demo.repository.CustomItemRepository;
//import org.junit.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.mongodb.core.BulkOperations;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import static net.bytebuddy.matcher.ElementMatchers.any;
//import static org.assertj.core.api.BDDAssertions.then;
//import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Cond.when;
////import static org.mockito.Mockito.*;
//import static org.mockito.ArgumentMatchers.*;
//
//@DataMongoTest
//@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
//@DirtiesContext
//public class CustomItemRepositoryTest {
//
////    @InjectMocks
//    private CustomItemRepositoryImpl customItemRepository;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
////    @Mock
////    private BulkOperations bulkInsertion;
//
//    @Test
//    public void bulkCreateItems_returnTrue() {
//        customItemRepository = new CustomItemRepositoryImpl(mongoTemplate);
//        Item item = new Item("id", "name", "category", 1);
//        List<Item> itemList = new ArrayList<>();
//        itemList.add(item);
////        Mockito.when(mongoTemplate.bulkOps(any(), (Class<?>) any())).thenReturn(bulkInsertion);
//
//        int result = customItemRepository.bulkCreateItems(itemList);
//
//        then(1).isEqualTo(result);
//
//
//    }
//}
