package xuanhieu.kafka.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import xuanhieu.kafka.dao.ProductsDao;
import xuanhieu.kafka.entity.Products;
import xuanhieu.kafka.service.InventoryService;
import xuanhieu.kafka.service.ProductsService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {

    public static String jsonStringProduct;
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static Date current;
    public static String dateModifiedOnProduct;



    @Autowired
    ProductsDao productsDao;

    @Override
    public List<Products> findAllProducts() {
        return productsDao.findAllProducts();
    }

    @Override
    public Products getProductsByIdProduct(Integer id) {
        return productsDao.getProductsByIdProduct(id);
    }

    @Override
    public List<Products> getProductByNameProduct(String name) {
        return productsDao.getProductsByNameProduct(name);
    }

    @Override
    public Products createNewProduct(Products products) {
        products.setModifiedOn(products.getCreatedOn());
        return productsDao.createNewProduct(products);
    }

    @Override
    public Products updateProduct(Products products) {
        current = new Date();
        dateModifiedOnProduct = simpleDateFormat.format(current);
        products.setModifiedOn(dateModifiedOnProduct);
        return productsDao.updateProduct(products);
    }

    @Override
    public String deleteProductByIdProduct(Integer id) {
        return productsDao.deleteProductByIdProduct(id);
    }
}
