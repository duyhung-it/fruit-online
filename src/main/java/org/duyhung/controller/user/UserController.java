package org.duyhung.controller.user;

import jakarta.validation.Valid;
import org.duyhung.entity.User;
import org.duyhung.model.PasswordModel;
import org.duyhung.model.UserInformationModel;
import org.duyhung.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

@Controller("WebUserController")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/thong-tin-ca-nhan")
    public String getUserInformationPage(Model model, Principal principal){
        User user = userService.getUserByEmail(principal.getName());
        if(Objects.nonNull(user)){
            model.addAttribute("user",user);
            return "pages/web/thong-tin-khach-hang";
        }
        return "redirect:/login";
    }
    @GetMapping("/doi-mat-khau")
    public String getChangePasswordPage(Model model,
                                        @ModelAttribute("passwordModel")PasswordModel passwordModel,
                                        Authentication authentication
    ){
       if(authentication.isAuthenticated()){
           passwordModel.setEmail(authentication.getName());
           return "pages/authentication/change-password";
       }
        return "redirect:/login";
    }

    @PostMapping("/thong-tin-ca-nhan")
    public String updateUserInformation(@Valid @ModelAttribute("user") UserInformationModel userInformationModel, BindingResult result, Model model){
        if(result.hasErrors()){
            return "pages/web/thong-tin-khach-hang";
        }
        User user = mapToUser(userInformationModel);
        userService.saveUser(user);
        return "redirect:/thong-tin-ca-nhan";
    }
    @GetMapping("/xac-nhan")
    public String confirmMailPage(Model model,@RequestParam("email") String mail){
        model.addAttribute("email",mail);
        return "pages/authentication/confirm-mail";
    }

    private User mapToUser(UserInformationModel userInformationModel) {
        User user = userService.getUserById(userInformationModel.getId());
        user.setPhone(userInformationModel.getPhone());
        user.setName(userInformationModel.getName());
        user.setEmail(userInformationModel.getEmail());
        user.setGender(userInformationModel.isGender());
        user.setAddress(userInformationModel.getAddress());
        user.setDOB(userInformationModel.getDOB());
        return user;
    }
}
