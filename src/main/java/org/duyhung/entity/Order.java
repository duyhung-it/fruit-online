package org.duyhung.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GenericGenerator(name = "generator",strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Column(columnDefinition = "uniqueidentifier default NEWID()")
    private String id;
    @ManyToOne
    @JoinColumn(
            name = "users_id",
            referencedColumnName = "id",
            columnDefinition = "uniqueidentifier"
    )
    private User user;
    private String code;
    private String status;
    private Double totalPrice;
    @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private List<OrderDetail> orderDetails;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private LocalDate paidDate;
}
