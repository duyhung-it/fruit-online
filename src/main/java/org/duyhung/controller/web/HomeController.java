package org.duyhung.controller.web;

import jakarta.servlet.http.HttpSession;
import org.duyhung.entity.Cart;
import org.duyhung.entity.Product;
import org.duyhung.entity.User;
import org.duyhung.service.CartService;
import org.duyhung.service.ProductService;
import org.duyhung.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller(value = "webController")
public class HomeController {
    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;

    public HomeController(ProductService productService, CartService cartService, UserService userService) {
        this.productService = productService;
        this.cartService = cartService;
        this.userService = userService;
    }


    @RequestMapping(value = {"", "/", "trang-chu"})
    public String homePage(Model model,
                           @RequestParam(required = false, defaultValue = "1") Integer page,
                           @RequestParam(required = false, defaultValue = "8") Integer size,
                           HttpSession session,
                           Authentication authentication
    ) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate");
        Page<Product> page1 = productService.getAllProducts(pageable);
        model.addAttribute("list", page1.getContent());
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("listProducts", productService.getAllProducts());
        model.addAttribute("listPopularProducts",productService.findTopProduct(PageRequest.of(0,6)));
        model.addAttribute("currentPage",page);
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByEmail(authentication.getName());
            Cart cart = cartService.getCartsByUser(user);
            if (cart != null) {
                if (cart.getCartDetails().size() > 0)
                    session.setAttribute("cartProducts", cart.getCartDetails().size());
            }
        }
        return "pages/web/trang-chu";
    }

}
