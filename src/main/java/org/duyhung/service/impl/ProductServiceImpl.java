package org.duyhung.service.impl;

import org.duyhung.entity.Product;
import org.duyhung.repository.ProductRepository;
import org.duyhung.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        if(product.getId().isEmpty()) product.setId(null);
        product.setCreatedDate(LocalDate.now());
        product.setUpdatedDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        Product product =new Product();
        product.setId(id);
        productRepository.delete(product);
    }

    @Override
    public Page<Product> findAllByCategory_Id(String categoryId, Pageable pageable) {
        return productRepository.findAllByCategory_Id(categoryId,pageable);
    }
}
