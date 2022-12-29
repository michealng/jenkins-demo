package com.example.demo.service;

import com.example.demo.dto.ItemDTO;
import com.example.demo.model.BaseItem;
import com.example.demo.model.Item;
import com.example.demo.model.ListResponse;
import com.example.demo.model.Response;
import com.example.demo.repository.CustomItemRepository;
import com.example.demo.repository.ItemMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemMongoRepository itemMongoRepository;

    @Autowired
    private CustomItemRepository customItemRepository;

//    public ItemService(ItemMongoRepository itemMongoRepository, CustomItemRepository customItemRepository) {
//        this.itemMongoRepository = itemMongoRepository;
//        this.customItemRepository = customItemRepository;
//    }

    public ListResponse findAllItem(int page, int size) {
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            Page<Item> items = itemMongoRepository.findAll(pageRequest);
            ListResponse customResponse = new ListResponse();
            List<BaseItem> itemList = new ArrayList<>();
            ItemDTO item;
            for (Item tmp: items.getContent()) {
                item = new ItemDTO(tmp);
                itemList.add(item);
            }
            customResponse.setData(itemList);
            customResponse.setMessage("Found data");
            customResponse.setPageCount(items.getTotalPages());
            customResponse.setPageNumber(items.getNumber());
            customResponse.setPageSize(items.getSize());
            customResponse.setTotalRecords(items.getTotalElements());
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            ListResponse customResponse = new ListResponse();
            customResponse.setMessage("Cannot find data");
            customResponse.setError("Internal server error");
            return customResponse;
        }
    }

    public ListResponse getItemsByCategory(String category, int page, int size) {
        try {
            Pageable pageRequest = PageRequest.of(page, size);
            Page<Item> items = itemMongoRepository.findItemsByCategory(category, pageRequest);
            ListResponse customResponse = new ListResponse();
            List<BaseItem> itemList = new ArrayList<>();
            ItemDTO item;
            for (Item tmp: items.getContent()) {
                item = new ItemDTO(tmp);
                itemList.add(item);
            }
            customResponse.setData(itemList);
            customResponse.setMessage("Found data");
            customResponse.setPageCount(items.getTotalPages());
            customResponse.setPageNumber(items.getNumber());
            customResponse.setPageSize(items.getSize());
            customResponse.setTotalRecords(items.getTotalElements());
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            ListResponse customResponse = new ListResponse();
            customResponse.setMessage("Cannot find data");
            customResponse.setError("Internal server error");
            return customResponse;
        }
    }

    public Response findOneItem(String itemId) {
        try {
            Optional<Item> itemOptional = itemMongoRepository.findById(itemId);
            Response customResponse = new Response();
            if (!itemOptional.isPresent()) {
                customResponse.setMessage("Cannot find item by id");
                return customResponse;
            }
            BaseItem item = new BaseItem();
            item.setName(itemOptional.get().getName());
            item.setCategory(itemOptional.get().getCategory());
            item.setQuantity(itemOptional.get().getQuantity());
            customResponse.setData(item);
            customResponse.setMessage("Found one item");
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            Response customResponse = new Response();
            customResponse.setMessage("Cannot find item by id");
            customResponse.setError("Internal server error");
            return customResponse;
        }
    }

    public Response createItem(BaseItem itemRequest) {
        try {
            Item item = new Item();
            item.setName((itemRequest.getName() != null) ? itemRequest.getName() : "");
            item.setCategory((itemRequest.getCategory() != null) ? itemRequest.getCategory() : "");
            item.setQuantity((itemRequest.getQuantity() != 0) ? itemRequest.getQuantity() : 0);
            itemMongoRepository.save(item);
            Response customResponse = new Response();
            BaseItem itemDTO = new BaseItem();
            itemDTO.setName(item.getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setCategory(item.getCategory());
            customResponse.setData(itemDTO);
            customResponse.setMessage("Item saved successfully");
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            Response customResponse = new Response();
            customResponse.setMessage("Cannot save item");
            customResponse.setError("Internal server error");
            return customResponse;
        }
    }

    public Response updateItem(String id, BaseItem itemRequest) {
        try {
            Optional<Item> itemOptional = itemMongoRepository.findById(id);
            Response customResponse = new Response();
            if (!itemOptional.isPresent()) {
                customResponse.setMessage("Cannot find item by id");
                return customResponse;
            }
            Item item = itemOptional.get();
            item.setName((itemRequest.getName() != null) ? itemRequest.getName() : item.getName());
            item.setCategory((itemRequest.getCategory() != null) ? itemRequest.getCategory() : item.getCategory());
            item.setQuantity((itemRequest.getQuantity() != null && itemRequest.getQuantity() != 0) ? itemRequest.getQuantity() : item.getQuantity());
            itemMongoRepository.save(item);
            BaseItem itemDTO = new BaseItem();
            itemDTO.setName(item.getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setCategory(item.getCategory());
            customResponse.setData(itemDTO);
            customResponse.setMessage("Item saved successfully");
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            Response customResponse = new Response();
            customResponse.setMessage("Cannot save item");
            customResponse.setError("Internal server error");
            return customResponse;
        }

    }

    public ListResponse createBulkItems(List<BaseItem> itemRequests) {
        try {
            List<Item> itemList = new ArrayList<>();
            Item item;
            for (BaseItem itemRequest: itemRequests) {
                item = new Item(itemRequest);
                itemList.add(item);
            }
            int insertedItems = customItemRepository.bulkCreateItems(itemList);
            ListResponse customResponse = new ListResponse();
            if (insertedItems == 0) {
                customResponse.setMessage("Cannot save item");
                customResponse.setError("Internal server error");
                return customResponse;
            }
            customResponse.setMessage("Item saved successfully");
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            ListResponse customResponse = new ListResponse();
            customResponse.setMessage("Cannot save item");
            customResponse.setError("Internal server error");
            return customResponse;
        }
    }

    public Response getItemByName(String name) {
        try {
            Item item = itemMongoRepository.findItemByName(name);
            Response customResponse = new Response();
            if (item == null) {
                customResponse.setMessage("Cannot find item by name: " + name);
                return customResponse;
            }
            BaseItem itemDTO = new BaseItem();
            itemDTO.setName(item.getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setCategory(item.getCategory());
            customResponse.setData(itemDTO);
            customResponse.setMessage("Found one item");
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            Response customResponse = new Response();
            customResponse.setMessage("Cannot find item by name: " + name);
            customResponse.setError("Internal server error");
            return customResponse;
        }
    }

    public Response deleteItemById(String id) {
        try {
            itemMongoRepository.deleteById(id);
            Response customResponse = new Response();
            customResponse.setMessage("Item deleted successfully");
            return customResponse;
        } catch (Exception e) {
            e.printStackTrace();
            Response customResponse = new Response();
            customResponse.setMessage("Cannot delete item");
            customResponse.setError("Internal server error");
            return customResponse;
        }
    }

}
