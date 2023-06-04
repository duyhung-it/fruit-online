package org.duyhung.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterModel {
    @Email
    @NotBlank(message = "Vui lòng nhập email")
    private String email;
    @NotBlank(message = "Vui long nhập mật khẩu")
    private String password;
    @NotBlank(message = "Vui lòng nhập họ tên")
    private String name;
    private String address;
    private Boolean gender;
}
