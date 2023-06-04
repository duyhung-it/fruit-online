package org.duyhung.service;

import org.duyhung.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface ProductService {


    List<Product> getAllProducts();
    Page<Product> getAllProducts(Pageable pageable);

    Product getProductById(String id);

    Product saveProduct(Product product);

    void deleteProduct(String id);
    Page<Product> findAllByCategory_Id(String categoryId, Pageable pageable);
}
