package xuanhieu.kafka.listener;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import xuanhieu.kafka.dto.InventoryDto;
import xuanhieu.kafka.entity.Inventory;
import xuanhieu.kafka.entity.Orders;

import java.util.Date;

@Service
public class ListenerConsumerInventory {

    private static final Gson gson = new Gson();
    private static Orders orders;

    @Autowired
    InventoryDto inventoryDto;

    @KafkaListener(topics = "update-inventory", groupId = "update-inventory-VXH")
    public String listenerUpdateInventory(String message) {
        orders = gson.fromJson(message,Orders.class);
        inventoryDto.updateInventoryByOrder(orders);
        return "";
    }
}
