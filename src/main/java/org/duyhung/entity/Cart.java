package org.duyhung.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.factory.internal.UUIDGenerationTypeStrategy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "carts")
public class Cart implements Serializable {
    @Id
    @GenericGenerator(name = "generator",strategy = "guid")
    @GeneratedValue(generator = "generator",strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default NEWID()")
    private String id;
    @OneToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "cart_id",referencedColumnName = "id")
    private List<CartDetail> cartDetails;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
