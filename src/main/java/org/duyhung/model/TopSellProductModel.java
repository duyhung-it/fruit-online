package org.duyhung.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.duyhung.entity.Product;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopSellProductModel {
    private Product product;
    private Long quantity;
}
