package org.duyhung.controller.web;

import org.duyhung.entity.Order;
import org.duyhung.entity.User;
import org.duyhung.service.OrderService;
import org.duyhung.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("OrderWebController")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    public OrderController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }


    @GetMapping("/hoa-don")
    public String getOrdersPage(Model model,
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "3") Integer size,
                                @RequestParam(required = false,defaultValue = "") String status,
                                Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByEmail(authentication.getName());
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate").descending());
            Page<Order> orderPage = null;
            if(status.isEmpty()){
                orderPage = orderService.getAllOrders(pageable);
            }else{
                model.addAttribute("status",status);
                orderPage = orderService.getAllOrdersByStatus(pageable,status);
            }
            model.addAttribute("list",orderPage.getContent());
            model.addAttribute("totalPages",orderPage.getTotalPages());
            model.addAttribute("currentPage",page);

            return "pages/web/hoa-don";
        }
        return "redirect:/login";
    }

    @GetMapping("/hoa-don/chi-tiet")
    public String getOrderDetailsPage(Model model, @RequestParam String id,
                                      Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByEmail(authentication.getName());
            Order order = orderService.getOrderById(id);
            model.addAttribute("order", order);
            model.addAttribute("user", user);
            model.addAttribute("status",Integer.parseInt(order.getStatus()) * 33);
            return "pages/web/chi-tiet-hoa-don";
        }
        return "redirect:/login";
    }
}
