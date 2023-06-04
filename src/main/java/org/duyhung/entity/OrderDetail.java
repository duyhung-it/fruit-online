package org.duyhung.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;
    private Long quantity;
    private Long price;
}
