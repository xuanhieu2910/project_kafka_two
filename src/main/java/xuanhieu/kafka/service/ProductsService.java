package xuanhieu.kafka.service;

import xuanhieu.kafka.entity.Products;

import java.util.List;

public interface ProductsService {
    List<Products> findAllProducts();
    Products getProductsByIdProduct(Integer id);
    Products createNewProduct(Products products);
    Products updateProduct(Products products);
    String deleteProductByIdProduct(Integer id);
}
