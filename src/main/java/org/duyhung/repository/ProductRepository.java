package org.duyhung.repository;

import org.duyhung.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findAllByCategory_Id(String categoryId, Pageable pageable);
}
