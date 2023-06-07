package org.duyhung.repository;

import org.duyhung.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);
    List<PasswordResetToken> findAllByUser_Id(String userId);
}