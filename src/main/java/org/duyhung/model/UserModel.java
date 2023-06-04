package org.duyhung.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private String code;
    private String name;
    private LocalDateTime dOB;
    private boolean gender;
    private String address;
    private String email;
    private String phone;
    @Email
    @NotBlank(message = "Please enter the email")
    private String username;
    @NotBlank(message = "Please enter the password")
    private String password;
    private boolean role;
}
