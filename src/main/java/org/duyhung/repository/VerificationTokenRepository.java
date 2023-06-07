package org.duyhung.repository;

import org.duyhung.entity.PasswordResetToken;
import org.duyhung.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
    List<VerificationToken> findAllByUser_Id(String userId);
}

