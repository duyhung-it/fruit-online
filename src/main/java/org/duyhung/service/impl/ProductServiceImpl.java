package org.duyhung.service.impl;

import org.duyhung.entity.Product;
import org.duyhung.model.TopSellProductModel;
import org.duyhung.repository.ProductRepository;
import org.duyhung.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    @Override
    public List<Product> findTopProduct(Pageable pageable) {
        List<Object[]> topSellProductModels = productRepository.findTopProduct(pageable);
        if(Objects.nonNull(topSellProductModels)){
            return topSellProductModels.stream().map(objects -> {
                return productRepository.findById((String) objects[0]).orElse(new Product());
            }).toList();
        }
        return null;
    }
    @Override
    public List<TopSellProductModel> findTopSellProduct(Pageable pageable){
        List<Object[]> topSellProductModels = productRepository.findTopProduct(pageable);
        return topSellProductModels.stream().map(objects -> {
            return new TopSellProductModel(productRepository.findById(objects[0].toString()).orElse(null),(long) objects[1]);
        }).toList();
    }
}
