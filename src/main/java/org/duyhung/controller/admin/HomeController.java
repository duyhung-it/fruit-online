package org.duyhung.controller.admin;

import org.duyhung.entity.Product;
import org.duyhung.entity.User;
import org.duyhung.service.CategoryService;
import org.duyhung.service.ProductService;
import org.duyhung.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller(value = "AdminController")
@RequestMapping("/admin")
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"","/trang-chu"})
    public String getAdminHomePage(){
        return "pages/admin/index";
    }
    @GetMapping("/users")
    public String getUserPage(Model model, @RequestParam(required = false) String action,
                              @RequestParam(value = "id",required = false) String id,
                              @RequestParam(required = false,defaultValue = "1") Integer page,
                              @RequestParam(required = false,defaultValue = "5") Integer size
    ){
        String redirectPage = "pages/admin/form-users";
        if( action.equalsIgnoreCase("add")){
            model.addAttribute("button","Add User");
            model.addAttribute("user",new User());
        } else if (action.equalsIgnoreCase("update")) {
            User user = userService.getUserById(id);
            model.addAttribute("user",user);
            model.addAttribute("button","Update User");
        }else{

            Page<User> userPage = userService.getAllUsers(PageRequest.of(page-1,size));
            List<User> list = userPage.getContent();
            redirectPage = "pages/admin/list-users";
            model.addAttribute("list",list);
            model.addAttribute("totalPages",userPage.getTotalPages());
            model.addAttribute("currentPage",page);
        }
        return redirectPage;
    }


}
