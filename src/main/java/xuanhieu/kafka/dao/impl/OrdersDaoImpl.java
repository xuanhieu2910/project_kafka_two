package xuanhieu.kafka.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import xuanhieu.kafka.dao.OrdersDao;
import xuanhieu.kafka.entity.Orders;
import xuanhieu.kafka.repository.OrdersRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrdersDaoImpl implements OrdersDao {

    private static final String NAME_TOPIC_UPDATE_INVENTORY = "update-inventory";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static String jsonStringOrder;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }


    @Override
    public Orders createNewOrders(Orders orders) {
        ordersRepository.save(orders);
        try {
            jsonStringOrder = objectMapper.writeValueAsString(orders);
            kafkaTemplate.send(NAME_TOPIC_UPDATE_INVENTORY, jsonStringOrder);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Orders getOrdersById(Integer id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if (orders.isPresent()) {
            return orders.get();
        }
        return null;
    }
}
