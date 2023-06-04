package org.duyhung.controller.admin;

import org.duyhung.entity.Category;
import org.duyhung.entity.Product;
import org.duyhung.entity.User;
import org.duyhung.model.UserModel;
import org.duyhung.service.CategoryService;
import org.duyhung.service.ProductService;
import org.duyhung.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller(value = "AdminController")
@RequestMapping("/admin")
public class HomeController {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = {"","/trang-chu"})
    public String getAdminHomePage(){
        return "pages/admin/index";
    }
    @GetMapping("/users")
    public String getUserPage(Model model, @RequestParam(required = false) String action, @RequestParam(value = "id",required = false) String id){
        String content = "";
        if( action.equalsIgnoreCase("add")){
            content = "form-users";
            model.addAttribute("button","Add User");
            model.addAttribute("user",new User());
        } else if (action.equalsIgnoreCase("update")) {
            content = "form-users";
            User user = userService.getUserById(id);
            model.addAttribute("user",user);
            model.addAttribute("button","Update User");
        }else{
            List<User> list = userService.getAllUsers();
            content = "list-users";
            model.addAttribute("list",list);
        }
        model.addAttribute("content",content);
        return "pages/admin/index";
    }


}
