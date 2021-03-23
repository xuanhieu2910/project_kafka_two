package xuanhieu.kafka.dto.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xuanhieu.kafka.dao.InventoryDao;
import xuanhieu.kafka.dto.InventoryDto;
import xuanhieu.kafka.entity.Inventory;
import xuanhieu.kafka.entity.OrderDetails;
import xuanhieu.kafka.entity.Orders;

import java.util.List;

@Service
public class InventoryDtoImpl implements InventoryDto {
    private static List<OrderDetails> orderDetails;
    private static Inventory inventory;

    @Autowired
    InventoryDao inventoryDao;


    @Override
    public void updateInventoryByOrder(Orders orders) {
        int quantityBefore=0;
        int quantityAfter=0;
        int idProduct;
        orderDetails = orders.getOrderDetailsList();
        for (int i = 0; i < orderDetails.size(); i++) {
            quantityBefore = orderDetails.get(i).getQuantity();
            idProduct = orderDetails.get(i).getProducts().getIdProduct();
            quantityAfter = inventoryDao.getInventoryByIdProduct(idProduct).getTotalSales() - quantityBefore;
            inventory = inventoryDao.getInventoryByIdProduct(idProduct);
            inventory.setTotalInventory(quantityAfter);
            inventory.setModifiedOn(orders.getCreatedOn());
            inventoryDao.updateInventory(inventory);
        }
    }
}
