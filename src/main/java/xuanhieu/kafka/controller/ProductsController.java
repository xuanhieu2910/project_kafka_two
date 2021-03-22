package xuanhieu.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import xuanhieu.kafka.entity.Products;
import xuanhieu.kafka.service.ProductsService;
import java.util.*;
@RestController
@RequestMapping(name = "/kafka")
public class ProductsController {
    @Autowired
    KafkaTemplate<String, String> productsKafkaTemplate;
    @Autowired
    ProductsService productsService;


    @GetMapping(value = "/products")
    public List<Products> getAllProducts() {
        return productsService.findAllProducts();
    }

    @GetMapping(value = "/product/{id}")
    public Products getProductByIdProduct(@PathVariable("id") Integer id) {
        return productsService.getProductsByIdProduct(id);
    }

    @PostMapping(value = "/create-product")
    public Products createProduct(@RequestBody Products products) {
        return productsService.createNewProduct(products);
    }


    @PutMapping(value = "/update-product/{id}")
    public Products updateProducts(@PathVariable("id") Integer id, @RequestBody Products products) {
        if (productsService.getProductsByIdProduct(id) != null) {
          return   productsService.updateProduct(products);
        }
        return null;
    }

    @DeleteMapping(value = "/delete-product/{id}")
    public String deleteProductById(@PathVariable("id") Integer id) {
        return productsService.deleteProductByIdProduct(id);
    }
}
