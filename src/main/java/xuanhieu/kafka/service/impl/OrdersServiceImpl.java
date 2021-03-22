package xuanhieu.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xuanhieu.kafka.dao.InventoryDao;
import xuanhieu.kafka.dao.OrderDetailsDao;
import xuanhieu.kafka.dao.OrdersDao;
import xuanhieu.kafka.dao.ProductsDao;
import xuanhieu.kafka.entity.Inventory;
import xuanhieu.kafka.entity.OrderDetails;
import xuanhieu.kafka.entity.Orders;
import xuanhieu.kafka.entity.Products;
import xuanhieu.kafka.service.OrdersService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    private static List<OrderDetails> orderDetailsList;
    private static List<OrderDetails> ordersList;
    private static OrderDetails orderDetails;
    private static Products products;
    private static Inventory inventory;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    OrdersDao ordersDao;

    @Autowired
    InventoryDao inventoryDao;

    @Autowired
    OrderDetailsDao orderDetailsDao;

    @Autowired
    ProductsDao productsDao;


    private boolean checkProductIntoInventory(List<OrderDetails> orderDetailsList) {
        for (int i = 0; i < orderDetailsList.size(); i++) {
            if (inventoryDao.getInventoryByIdProduct(orderDetailsList.get(i).getProducts().getIdProduct()) == null) {
                return false;
            }
        }
        return true;
    }


    private boolean checkTotalSalesInventory(List<OrderDetails> orderDetailsList) {
        for (int i = 0; i < orderDetailsList.size(); i++) {
            if (inventoryDao.getInventoryByIdProduct(orderDetailsList.get(i).
                    getProducts().getIdProduct()).getTotalSales() <= 0) {
                return false;
            }
        }
        return true;
    }


    private Integer totalQuantity(List<OrderDetails> orderDetailsList) {
        Integer totalQuantity = 0;
        for (int i = 0; i < orderDetailsList.size(); i++) {
            totalQuantity += orderDetailsList.get(i).getQuantity();
        }
        return totalQuantity;
    }


    private Float totalSales(List<OrderDetails> orderDetails) {
        float totalSales = 0;
        for (int i = 0; i < orderDetails.size(); i++) {
            totalSales += (orderDetails.get(i).getQuantity()) * (productsDao.getProductsByIdProduct(orderDetails.get(i).getProducts().getIdProduct()).getPrice());
        }
        return totalSales;
    }


    @Override
    public List<Orders> getAllOrders() {
        return ordersDao.getAllOrders();
    }

    @Override
    public Orders createNewOrders(Orders orders) {
        orderDetailsList = orders.getOrderDetailsList();
        if ((checkProductIntoInventory(orderDetailsList)) &&
                (checkTotalSalesInventory(orderDetailsList))){
            orders.setTotalQuantity(totalQuantity(orderDetailsList));
            orders.setTotalPrice(totalSales(orderDetailsList));
            ordersList = new ArrayList<>();
            for (int i = 0; i < orderDetailsList.size(); i++) {
                products = orderDetailsList.get(i).getProducts();
                orderDetails = orders.getOrderDetailsList().get(i);
                orderDetails.setOrders(orders);
                orderDetails.setProducts(products);
                ordersList.add(orderDetails);
            }
            orders.setOrderDetailsList(ordersList);
            return ordersDao.createNewOrders(orders);
        }
        return null;
    }

    @Override
    public Orders getOrdersById(Integer id) {
        return ordersDao.getOrdersById(id);
    }

    @Override
    public List<OrderDetails> getOrderDetailsByIdOrder(Integer id) {
        return orderDetailsDao.getOrderDetailsByIdOrder(id);
    }
}
