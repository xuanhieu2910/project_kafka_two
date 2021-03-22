package xuanhieu.kafka.service;

import xuanhieu.kafka.entity.Inventory;
import xuanhieu.kafka.entity.Products;

import java.util.*;

public interface InventoryService {

    List<Inventory> findAllInventory();

    Inventory getInventoryById(Integer id);

    void updateInventory(Inventory inventory);

    String deleteInventoryById(Integer id);

//    Inventory createInventoryFromCreateOnProduct(Products products);

}
