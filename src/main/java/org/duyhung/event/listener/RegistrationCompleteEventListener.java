package org.duyhung.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.duyhung.entity.User;
import org.duyhung.event.RegistrationCompleteEvent;
import org.duyhung.service.UserService;
import org.duyhung.service.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Slf4j
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;
    private final EmailServiceImpl emailService;

    public RegistrationCompleteEventListener(UserService userService, EmailServiceImpl emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //create the verification Token for user with Link
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token, user);
        // send email to user
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("hungndph26995@fpt.edu.vn");
        String url = event.getUrl() + "/verifyRegistration?token=" + token;
        mailMessage.setText("To confirm your account, please click here : "
                +url);

        emailService.sendEmail(mailMessage);
        //sendVerificationEmail()
        log.info("Click the link to verify your account: {} ", url);
    }
}
