package org.duyhung.controller.user.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.duyhung.entity.PasswordResetToken;
import org.duyhung.entity.User;
import org.duyhung.entity.VerificationToken;
import org.duyhung.event.RegistrationCompleteEvent;
import org.duyhung.model.PasswordModel;
import org.duyhung.model.RegisterModel;
import org.duyhung.model.UserModel;
import org.duyhung.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    public RegistrationController(UserService userService, ApplicationEventPublisher publisher) {
        this.userService = userService;
        this.publisher = publisher;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterModel registerModel, final HttpServletRequest request){
        User user = userService.registerUser(registerModel);
        publisher.publishEvent(new RegistrationCompleteEvent(
                user,
                applicationUrl(request)
        ));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam(name = "token") String token){
        String result = userService.validateVerificationToken(token);
        if(result.equals("valid")){
            return "User verify successfully";
        }
        return "Bad user";
    }
    @GetMapping("/resendVerificationToken")
    public String resendRegistrationToken(@RequestParam(name = "token") String oldToken,
                                          HttpServletRequest request){
        VerificationToken token = userService.generateNewVerificationToken(oldToken);
//        publisher.publishEvent(new ResendVerificationTokenCompleteEvent(
//                token,applicationUrl(request)
//        ));
        return "Verification link sent";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request){
        User user = userService.getUserByEmail(passwordModel.getEmail());
        String url = "";
        if(user != null){
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken =
                    userService.createPasswordResetTokenForUser(user,token);
            url = passwordResetTokenMail(user,applicationUrl(request),token);
        }
        return url;
    }
    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,@RequestBody PasswordModel passwordModel){
        String valid = userService.validateResetPasswordToken(token);
        if(!valid.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password Reset Successfully";
        }
        return "Invalid Token";

    }
    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody PasswordModel passwordModel,HttpStatus status){
        User user = userService.getUserByEmail(passwordModel.getEmail());
        if(!userService.checkIfValidOldPassword(user,passwordModel.getOldPassword())){
            return new ResponseEntity<>("Mật khẩu cũ không chính xác!",HttpStatus.BAD_REQUEST);
        }
        //save new password
        userService.changePassword(user,passwordModel.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "responseText","Thay đổi mật khẩu thành công",
                "statusText","success"
        ));
    }
    private String passwordResetTokenMail(User user, String applicationUrl,String token) {
        return applicationUrl + "/savePassword?token=" + token;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName()
                + ":" + request.getServerPort()
                + request.getContextPath();
    }
}
