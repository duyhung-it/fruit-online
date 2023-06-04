package org.duyhung.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category implements Serializable {
    @Id
    @GenericGenerator(name = "generator",strategy = "guid")
    @GeneratedValue(generator = "generator")
    @Column(columnDefinition = "uniqueidentifier default NEWID()")
    private String id;
    @NotBlank(message = "Vui lòng nhập mã!")
    @Column(columnDefinition = "varchar(15)")
    private String code;
    @NotBlank(message = "Vui lòng nhập tên!")
    @Column(columnDefinition = "nvarchar(50)")
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedDate;

    @Override
    public String toString() {
//        return "{ " +
//                " 'id' : " + "'" + id + "'"+
//                ", 'code' : " + "'" + code + "'" +
//                ", 'name' : " + "'" + name + "'" +
//                " }";
        ObjectWriter objectWriter = JsonMapper.builder()
                .findAndAddModules()
                .build().writer().withDefaultPrettyPrinter();
        try {
           return objectWriter.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
