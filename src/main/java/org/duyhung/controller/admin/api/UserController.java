package org.duyhung.controller.admin.api;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.duyhung.entity.User;
import org.duyhung.service.UserService;
import org.duyhung.validation.ValidationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ValidationModel validationModel;

    @PostMapping("/users")
    public ResponseEntity<Map<String,String>> saveUser(@Valid @RequestBody User user, Errors errors){

        if(user != null){
            validationModel.validateUserCode(userService.getUserByCode(user.getCode()),errors);
            validationModel.validateUserEmail(userService.getUserByEmail(user.getEmail()),errors);
            if(errors.hasErrors()){
                Map<String,String> map = errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
                return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
            }
        }
         User savedUser = userService.saveUser(user);
         return new ResponseEntity<>(new HashMap<>(), HttpStatus.CREATED);
    }
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        log.info(user.getCode());
        User updatedUser = userService.saveUser(user);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }

}
