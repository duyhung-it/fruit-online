package org.duyhung.controller.user;

import jakarta.servlet.http.HttpSession;
import org.duyhung.entity.User;
import org.duyhung.model.UserModel;
import org.duyhung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        UserModel userModel = new UserModel();
        model.addAttribute("user",userModel);
        return "pages/authentication/login";
    }
    @GetMapping("/register")
    public String getSignupPage(Model model){
        UserModel userModel = new UserModel();
        model.addAttribute("user",userModel);
        return "pages/authentication/register";
    }
    @PostMapping("/process-login")
    public String login(Model model, HttpSession session, @ModelAttribute(name = "user") UserModel userModel){
        User user = userService.getUserByEmail(userModel.getUsername());
        if(!(user != null && userService.checkUserPassword(userModel.getPassword(),user.getPassword()))){
            model.addAttribute("message","Email or password not corrected");
            return "pages/authentication/login";
        }
        session.setAttribute("user",user);
        if(user.getRole()){
            return "redirect:/admin";
        }
        return "redirect:/trang-chu";
    }
}
