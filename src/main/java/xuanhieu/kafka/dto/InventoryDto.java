package xuanhieu.kafka.dto;

import xuanhieu.kafka.entity.Inventory;
import xuanhieu.kafka.entity.Orders;

public interface InventoryDto {

    void updateInventoryByOrder(Orders orders);
}
