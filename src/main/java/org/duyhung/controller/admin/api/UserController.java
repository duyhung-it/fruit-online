package org.duyhung.controller.admin.api;

import jakarta.validation.Valid;
import org.duyhung.entity.User;
import org.duyhung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
         User savedUser = userService.saveUser(user);
         return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        User updatedUser = userService.saveUser(user);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("/users")
    public void deleteUser(@RequestBody User user){
        userService.deleteUser(user);
    }

}
