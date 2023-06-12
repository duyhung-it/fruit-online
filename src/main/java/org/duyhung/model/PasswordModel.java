package org.duyhung.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordModel {
    private String email;
    @NotBlank(message = "Vui lòng nhập mật khẩu cũ")
    private String oldPassword;
    @NotBlank(message = "Vui lòng nhập mật khẩu mới")
    private String newPassword;
}
