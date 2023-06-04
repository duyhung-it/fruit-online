package org.duyhung.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GenericGenerator(name = "generator",strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Column(columnDefinition = "uniqueidentifier default NEWID()")
    private String id;
    @NotBlank(message = "Vui long nhap ma")
    @Column(unique = true)
    private String code;
    @NotBlank(message = "Vui long nhap ten")
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @NotNull(message = "Vui long nhap gia")
    @Min(value = 0, message = "Gia khong duoc am")
    private Long price;
    @NotNull(message = "Vui long nhap so luong")
    @Min(value = 0, message = "So luong khong duoc am")
    private Long quantity;
    @Column(columnDefinition = "nvarchar(255)")
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ManyToOne()
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;
    @NotBlank(message = "Vui long chon image")
    private String image;
    @Temporal(TemporalType.DATE)
    private LocalDate createdDate;
    private LocalDateTime updatedDate;
}
