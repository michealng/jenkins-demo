package com.example.demo.controller;

import com.example.demo.api.ItemsApi;
import com.example.demo.model.BaseItem;
import com.example.demo.model.ListResponse;
import com.example.demo.model.Response;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping(value = "/api/v1")
public class ItemController implements ItemsApi {
    @Autowired
    private ItemService itemService;

    @Override
    public ResponseEntity<ListResponse> addManyItems(List<BaseItem> itemList) {
        ListResponse response = itemService.createBulkItems(itemList);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Response> addOneItem(BaseItem itemRequest) {
        Response response = itemService.createItem(itemRequest);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Response> deleteItem(String id) {
        Response response = itemService.findOneItem(id);

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response = itemService.deleteItemById(id);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ListResponse> findAllItems(@RequestParam(required = false) String category,
                                                     @RequestParam(defaultValue = "0") Integer page,
                                                     @RequestParam(defaultValue = "3") Integer size) {
        ListResponse response;
        if (category == null) {
            response = itemService.findAllItem(page, size);
        } else {
            response = itemService.getItemsByCategory(category, page, size);
        }

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> findItemByName(String name) {
        Response response = itemService.getItemByName(name);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Response> updateItemById(String id, BaseItem itemRequest) {
        Response response = itemService.findOneItem(id);

        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (response.getData() == null) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response = itemService.updateItem(id, itemRequest);
        if (response.getError() != null) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
