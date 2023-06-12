package org.duyhung.repository;

import org.duyhung.entity.Category;
import org.duyhung.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    public void saveProductTest(){
        Category category = new Category();
        category.setCode("C1");
        category.setName("Fresh Fruit");
        category.setCreatedDate(LocalDateTime.now());
        category.setUpdatedDate(LocalDateTime.now());
        Product product = Product.builder()
                .name("Watermelon")
                .code("w1")
                .price((long) 6000.0)
                .description("Fresh fruit by Kien")
                .quantity(110L)
                .category(category)
                .createdDate(LocalDate.now())
                .build();
        productRepository.save(product);
    }
    @Test
    public void findByCategory(){
        productRepository.findAllByCategory_Id("8BCD4F31-4A88-4F2B-896C-7BFEE158E160", PageRequest.of(0,5));
    }
    @Test
    public void findTopProduct(){
        productRepository.findTopProduct(PageRequest.of(0,3)).forEach(System.out::println);
    }
}