package com.example.demo.controller;

import com.example.demo.model.BaseItem;
import com.example.demo.model.ListResponse;
import com.example.demo.model.Response;
import com.example.demo.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ItemService itemService;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addManyItems_return201() throws Exception {
        // Given
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
        List<BaseItem> itemList = new ArrayList<>();
        itemList.add(baseItem);

        ListResponse response = new ListResponse();
        response.setData(itemList);
        response.setMessage("Item saved successfully");

        // When
        Mockito.when(itemService.createBulkItems(itemList)).thenReturn(response);

        // Then
        mockMvc.perform(post("/api/v1/items/bulk")
                        .content(asJsonString(itemList))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.message", is("Item saved successfully")));

    }

    @Test
    public void addManyItems_return500() throws Exception {
        // Given
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
        List<BaseItem> itemList = new ArrayList<>();
        itemList.add(baseItem);

        ListResponse response = new ListResponse();
        response.setMessage("Cannot save item");
        response.setError("Internal server error");

        // When
        Mockito.when(itemService.createBulkItems(itemList)).thenReturn(response);

        // Then
        mockMvc.perform(post("/api/v1/items/bulk")
                        .content(asJsonString(itemList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot save item")));
    }

    @Test
    public void addOneItem_return201() throws Exception {
        // Given
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
        response.setData(baseItem);
        response.setMessage("Item saved successfully");

        // When
        Mockito.when(itemService.createItem(baseItem)).thenReturn(response);

        // Then
        mockMvc.perform(post("/api/v1/items")
                        .content(asJsonString(baseItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Item saved successfully")));

    }

    @Test
    public void addOneItem_return500() throws Exception {
        // Given
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
        response.setData(baseItem);
        response.setMessage("Cannot save item");
        response.setError("Internal server error");

        // When
        Mockito.when(itemService.createItem(baseItem)).thenReturn(response);

        // Then
        mockMvc.perform(post("/api/v1/items")
                        .content(asJsonString(baseItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot save item")));
    }

    @Test
    public void deleteItem_return204() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
        response.setData(baseItem);
        response.setMessage("Item deleted successfully");

        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(response);
        Mockito.when(itemService.deleteItemById(any(String.class))).thenReturn(response);

        mockMvc.perform(delete("/api/v1/items/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteItem_return404() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
//        response.setData(baseItem);
        response.setMessage("Cannot find item by id");


        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(response);

        mockMvc.perform(delete("/api/v1/items/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteItem_return500_findError() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
//        response.setData(baseItem);
        response.setMessage("Cannot find item by id");
        response.setError("Internal server error");


        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(response);

        mockMvc.perform(delete("/api/v1/items/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot find item by id")));
    }

    @Test
    public void deleteItem_return500_deleteError() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response firstResponse = new Response();
        firstResponse.setData(baseItem);

        Response response = new Response();
//        response.setData(baseItem);
        response.setMessage("Cannot delete item");
        response.setError("Internal server error");


        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(firstResponse);
        Mockito.when(itemService.deleteItemById(any(String.class))).thenReturn(response);

        mockMvc.perform(delete("/api/v1/items/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot delete item")));
    }

    @Test
    public void findAllItems_return200() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
        List<BaseItem> itemList = new ArrayList<>();
        itemList.add(baseItem);

        ListResponse response = new ListResponse();
        response.setData(itemList);
        response.setMessage("Found data");

        Mockito.when(itemService.findAllItem(0, 5)).thenReturn(response);

        // Then
        mockMvc.perform(get("/api/v1/items")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Found data")));
    }

    @Test
    public void findAllItems_return500() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
        List<BaseItem> itemList = new ArrayList<>();
        itemList.add(baseItem);

        ListResponse response = new ListResponse();
//        response.setData(itemList);
        response.setMessage("Cannot find data");
        response.setError("Internal server error");

        Mockito.when(itemService.findAllItem(0, 5)).thenReturn(response);

        // Then
        mockMvc.perform(get("/api/v1/items")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot find data")));
    }

    @Test
    public void findItemByName_return200() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
        response.setData(baseItem);
        response.setMessage("Found one item");

        // When
        Mockito.when(itemService.getItemByName(any(String.class))).thenReturn(response);

        // Then
        mockMvc.perform(get("/api/v1/items/{name}", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Found one item")));
    }

    @Test
    public void findItemByName_return500() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
        response.setData(baseItem);
        response.setMessage("Cannot find item by name: name");
        response.setError("Internal server error");

        // When
        Mockito.when(itemService.getItemByName(any(String.class))).thenReturn(response);

        // Then
        mockMvc.perform(get("/api/v1/items/{name}", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot find item by name: name")));
    }

    @Test
    public void updateItemById_return200() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
        response.setData(baseItem);
        response.setMessage("Item deleted successfully");

        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(response);
        Mockito.when(itemService.updateItem(any(String.class), any(BaseItem.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/items/{id}", "1")
                        .content(asJsonString(baseItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Item deleted successfully")));
    }

    @Test
    public void updateItemById_return404() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
//        response.setData(baseItem);
        response.setMessage("Cannot find item by id");


        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/items/{id}", "1")
                        .content(asJsonString(baseItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateItemById_return500_findError() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response response = new Response();
//        response.setData(baseItem);
        response.setMessage("Cannot find item by id");
        response.setError("Internal server error");


        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/items/{id}", "1")
                        .content(asJsonString(baseItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot find item by id")));
    }

    @Test
    public void updateItemById_return500_updateError() throws Exception {
        BaseItem baseItem = new BaseItem();
        baseItem.setName("A");
        baseItem.setCategory("B");
        baseItem.setQuantity(1);
//        List<BaseItem> itemList = new ArrayList<>();
//        itemList.add(baseItem);

        Response firstResponse = new Response();
        firstResponse.setData(baseItem);

        Response response = new Response();
//        response.setData(baseItem);
        response.setMessage("Cannot save item");
        response.setError("Internal server error");


        Mockito.when(itemService.findOneItem(any(String.class))).thenReturn(firstResponse);
        Mockito.when(itemService.updateItem(any(String.class), any(BaseItem.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/items/{id}", "1")
                        .content(asJsonString(baseItem))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot save item")));
    }


}
