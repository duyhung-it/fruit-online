package org.duyhung.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationModel {
    private String id;
    @NotBlank(message = "Vui lòng nhập họ tên")
    private String name;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Vui lòng chọn ngày sinh")
    private Date dOB;
    private boolean gender;
    @NotBlank(message = "Vui lòng nhập địa chỉ")
    private String address;
    @Email
    @NotBlank(message = "Vui lòng nhập email")
    private String email;
    @NotBlank(message = "Vui lòng nhập số điện thoại")
    private String phone;
}
