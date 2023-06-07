package org.duyhung.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.duyhung.entity.PasswordResetToken;
import org.duyhung.entity.User;
import org.duyhung.entity.VerificationToken;
import org.duyhung.model.RegisterModel;
import org.duyhung.repository.PasswordResetTokenRepository;
import org.duyhung.repository.UserRepository;
import org.duyhung.repository.VerificationTokenRepository;
import org.duyhung.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final VerificationTokenRepository tokenRepository;
    private static final Comparator<User> EMPTY_COMPARATOR = (e1, e2) -> 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordResetTokenRepository, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.tokenRepository = tokenRepository;
    }
    public User saveUser(User user){
        if(user.getId().isBlank()){
            user.setId(null);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }
    @Transactional
    @Override
    public void deleteUser(User user) {
        List<VerificationToken> verificationTokens =
                tokenRepository.findAllByUser_Id(user.getId());
        if(!verificationTokens.isEmpty()){
            for (VerificationToken p:
                 verificationTokens) {
                tokenRepository.deleteById(p.getId());
            }
        }
        userRepository.deleteById(user.getId());
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndEnabledIsTrue(email).orElse(null);
    }

    @Override
    public boolean checkUserPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public org.springframework.data.domain.Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(String id) {

        return userRepository.findById(id).orElse(new User());
    }
    @Override
    public User registerUser(RegisterModel registerModel) {
        User user = new User();
        user.setCode(new Date().getTime() + "");
        user.setEmail(registerModel.getEmail());
        user.setName(registerModel.getName());
        user.setPassword(passwordEncoder.encode(registerModel.getPassword()));
        user.setRole(false);
        return userRepository.save(user);
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user,token);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if(verificationToken == null){
            return "invalid";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <=0){
            tokenRepository.delete(verificationToken);
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(oldToken);
        if(verificationToken != null){
            verificationToken.setToken(UUID.randomUUID().toString());
            return tokenRepository.save(verificationToken);
        }
        return null;
    }

    @Override
    public PasswordResetToken createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user,token);
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validateResetPasswordToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null){
            return "invalid";
        }
        User user = passwordResetToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if(passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <=0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword,user.getPassword());
    }
}
