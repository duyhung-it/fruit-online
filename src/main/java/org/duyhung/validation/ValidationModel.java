package org.duyhung.validation;

import org.duyhung.entity.CartDetail;
import org.duyhung.entity.User;
import org.duyhung.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ValidationModel {
    private UserService userService;
    public void validateQuantityProductToCart(CartDetail cartDetail, Errors error){
//        if(cartDetail.getQuantity() >= cartDetail.getId().getProduct().getQuantity()){
////            error.reject("");
//        }
    }
    public void validateUserCode(User user,Errors errors){
        if(user != null){
            errors.rejectValue("code","test.testCode.exist","Mã đã tồn tại!");
        }
    }
    public void validateUserEmail(User user,Errors errors){
        if(user != null){
            errors.rejectValue("email","test.testEmail.exist","Email đã tồn tại!");
        }
    }
}
