package org.duyhung.repository;

import org.duyhung.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmailAndEnabledIsTrue(String email);

    Optional<User> findByEmail(String email);

    Integer countUserByRoleIsFalse();

    User findByCode(String code);
}
