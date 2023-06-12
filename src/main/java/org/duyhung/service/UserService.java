package org.duyhung.service;

import org.duyhung.entity.PasswordResetToken;
import org.duyhung.entity.User;
import org.duyhung.entity.VerificationToken;
import org.duyhung.model.RegisterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserByEmail(String email);
    User getUserByCode(String code);

    boolean checkUserPassword(String password, String password1);

    List<User> getAllUsers();
    Page<User> getAllUsers(Pageable pageable);

    User getUserById(String id);

    User saveUser(User user);

    void deleteUser(User user);
    void saveVerificationTokenForUser(String token, User user);
    User registerUser(RegisterModel userModel);
    String validateVerificationToken(String token);
    VerificationToken generateNewVerificationToken(String oldToken);
    PasswordResetToken createPasswordResetTokenForUser(User user, String token);
    String validateResetPasswordToken(String token);
    Optional<User> getUserByPasswordResetToken(String token);
    void changePassword(User user, String newPassword);
    boolean checkIfValidOldPassword(User user, String oldPassword);
    Integer countUserByRoleIsFalse();
}
