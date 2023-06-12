package org.duyhung.repository;

import org.duyhung.entity.Product;
import org.duyhung.model.TopSellProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {
    Page<Product> findAllByCategory_Id(String categoryId, Pageable pageable);
    @Query(value = "select o.id.product.id,count(o.id.product) " +
            "from OrderDetail o " +
            "group by o.id.product " +
            "order by count(o.id.product) desc ")
    List<Object[]> findTopProduct(Pageable pageable);
}
