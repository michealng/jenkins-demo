package com.example.demo.service;

import com.example.demo.model.BaseItem;
import com.example.demo.model.Item;
import com.example.demo.model.ListResponse;
import com.example.demo.model.Response;
import com.example.demo.repository.CustomItemRepository;
import com.example.demo.repository.ItemMongoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemMongoRepository itemMongoRepository;

    @Mock
    private CustomItemRepository customItemRepository;

    String category = "category";

    String id = "id";

    String name = "name";

    BaseItem itemRequest = new BaseItem();

    Item item;

    List<Item> listItem = new ArrayList<>();

    Page<Item> pageItem;

    Optional<Item> itemOptional;

    Optional<Item> itemOptional1 = Optional.empty();

    List<BaseItem> listItemReq = new ArrayList<>();

    @Before
    public void setUp() {
        // Given
        itemRequest.setName("name");
        itemRequest.setCategory("category");
        itemRequest.setQuantity(1);
        item = new Item("id", "name", "category", 1);

        listItem.add(item);
        listItemReq.add(itemRequest);
        pageItem = new PageImpl<>(listItem);
        itemOptional = Optional.of(item);

        //  When
//        Mockito.when(itemMongoRepository.findAll(any(Pageable.class))).thenReturn(pageItem);
//        Mockito.when(itemMongoRepository.findItemsByCategory(any(String.class), any(Pageable.class))).thenReturn(pageItem);
//        Mockito.when(itemMongoRepository.findById(id)).thenReturn(itemOptional);
//        Mockito.when(itemMongoRepository.save(any())).thenReturn(item);
//        Mockito.when(customItemRepository.bulkCreateItems(listItem)).thenReturn(1);
//        Mockito.when(itemMongoRepository.findItemByName(name)).thenReturn(item);
//        doNothing().when(itemMongoRepository).deleteById(id);
    }

    @Test
    public void whenFindAllItem_returnTrue() {
        Mockito.when(itemMongoRepository.findAll(any(Pageable.class))).thenReturn(pageItem);
        // Then
        ListResponse response = itemService.findAllItem(0,5);

        assertThat(response.getMessage()).isEqualTo("Found data");
    }

    @Test
    public void whenFindAllItem_returnException() {
        Mockito.when(itemMongoRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException());

        ListResponse response = itemService.findAllItem(0,5);

        assertThat(response.getMessage()).isEqualTo("Cannot find data");
    }

    @Test
    public void whenGetItemsByCategory_returnTrue() {
        Mockito.when(itemMongoRepository.findItemsByCategory(any(String.class), any(Pageable.class))).thenReturn(pageItem);
        // Then
        ListResponse response = itemService.getItemsByCategory(category, 0, 5);

        assertThat(response.getMessage()).isEqualTo("Found data");

    }

    @Test
    public void whenGetItemsByCategory_returnException() {
        Mockito.when(itemMongoRepository.findItemsByCategory(any(String.class), any(Pageable.class))).thenThrow(new RuntimeException());
        // Then
        ListResponse response = itemService.getItemsByCategory(category, 0, 5);

        assertThat(response.getMessage()).isEqualTo("Cannot find data");

    }

    @Test
    public void whenFindOneItem_returnTrue() {
        Mockito.when(itemMongoRepository.findById(id)).thenReturn(itemOptional);
        // Then
        Response response = itemService.findOneItem(id);

        assertThat(response.getMessage()).isEqualTo("Found one item");
    }

    @Test
    public void whenFindOneItem_returnException() {
        Mockito.when(itemMongoRepository.findById(id)).thenThrow(new RuntimeException());

        // Then
        Response response = itemService.findOneItem(id);

        assertThat(response.getMessage()).isEqualTo("Cannot find item by id");
    }

    @Test
    public void createItem_returnTrue() {
        Mockito.when(itemMongoRepository.save(any())).thenReturn(item);
        // Then
        Response response = itemService.createItem(itemRequest);

        assertThat(response.getMessage()).isEqualTo("Item saved successfully");

    }

    @Test
    public void createItem_returnException() {
        Mockito.when(itemMongoRepository.save(any())).thenThrow(new RuntimeException());
        // Then
        Response response = itemService.createItem(itemRequest);

        assertThat(response.getMessage()).isEqualTo("Cannot save item");

    }

    @Test
    public void updateItem_returnTrue() {
        Mockito.when(itemMongoRepository.findById(id)).thenReturn(itemOptional);
        Mockito.when(itemMongoRepository.save(any())).thenReturn(item);
        // Then
        Response response = itemService.updateItem(id, itemRequest);

        assertThat(response.getMessage()).isEqualTo("Item saved successfully");
    }

    @Test
    public void updateItem_returnException1() {
        Mockito.when(itemMongoRepository.findById(id)).thenReturn(itemOptional1);
//        Mockito.when(itemMongoRepository.save(any())).thenThrow(new RuntimeException());

        // Then
        Response response = itemService.updateItem(id, itemRequest);

        assertThat(response.getMessage()).isEqualTo("Cannot find item by id");
    }

    @Test
    public void updateItem_returnException2() {
        Mockito.when(itemMongoRepository.findById(id)).thenReturn(itemOptional);
        Mockito.when(itemMongoRepository.save(any())).thenThrow(new RuntimeException());

        // Then
        Response response = itemService.updateItem(id, itemRequest);

        assertThat(response.getMessage()).isEqualTo("Cannot save item");
    }

    @Test
    public void createBulkItems_returnTrue() {
        Mockito.when(customItemRepository.bulkCreateItems(any())).thenReturn(1);

        ListResponse response = itemService.createBulkItems(listItemReq);

        assertThat(response.getMessage()).isEqualTo("Item saved successfully");
    }

    @Test
    public void createBulkItems_returnException() {
        Mockito.when(customItemRepository.bulkCreateItems(listItem)).thenThrow(new RuntimeException());

        ListResponse response = itemService.createBulkItems(listItemReq);

        assertThat(response.getMessage()).isEqualTo("Cannot save item");

    }

    @Test
    public void getItemByName_returnTrue() {
        Mockito.when(itemMongoRepository.findItemByName(name)).thenReturn(item);
        // Then
        Response response = itemService.getItemByName(name);

        assertThat(response.getMessage()).isEqualTo("Found one item");
    }

    @Test
    public void getItemByName_returnException() {
        Mockito.when(itemMongoRepository.findItemByName(name)).thenThrow(new RuntimeException());

        // Then
        Response response = itemService.getItemByName(name);

        assertThat(response.getMessage()).isEqualTo("Cannot find item by name: " + name);

    }

    @Test
    public void deleteItem_returnTrue() {
        doNothing().when(itemMongoRepository).deleteById(id);
        // Then
        Response response = itemService.deleteItemById(id);

        assertThat(response.getMessage()).isEqualTo("Item deleted successfully");
    }

    @Test
    public void deleteItem_returnException() {
        doThrow(new RuntimeException()).when(itemMongoRepository).deleteById(id);

        // Then
        Response response = itemService.deleteItemById(id);

        assertThat(response.getMessage()).isEqualTo("Cannot delete item");

    }

}
