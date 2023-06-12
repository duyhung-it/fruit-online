package org.duyhung.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GenericGenerator(name = "generator",strategy = "guid")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uniqueidentifier default NEWID()")
    private String id;
    @NotBlank(message = "Vui long nhap ma")
    @Column(unique = true,columnDefinition = "varchar(15)")
    private String code;
    @NotBlank(message = "Vui long nhap ten")
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dOB;
    private Boolean gender;
    @Column(columnDefinition = "nvarchar(255)")
    private String address;
    private String phone;
    @NotBlank(message = "Vui long nhap email")
    @Email(message = "Email sai dinh dang")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Vui long nhap password")
    private String password;
    @Column(name = "role",columnDefinition = "bit")
    private Boolean role;
    @Column(name = "enabled",columnDefinition = "bit")
    private Boolean enabled;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
