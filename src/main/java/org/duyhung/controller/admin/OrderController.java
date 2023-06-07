package org.duyhung.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller("OrderAdminController")
@RequestMapping("/admin")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {

        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String getOrderPage(Model model,
                               @RequestParam(required = false,defaultValue = "1") Integer page,
                               @RequestParam(required = false,defaultValue = "5") Integer size,
                               @RequestParam(required = false,defaultValue = "") String status){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDate","status").descending());
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
        return "pages/admin/list-orders";
    }
    @GetMapping("/orders/detail")
    public String getOrderDetailsPage(Model model, @RequestParam String id,
                                      Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByEmail(authentication.getName());
            Order order = orderService.getOrderById(id);
            model.addAttribute("content", "order-details");
            model.addAttribute("order", order);
            model.addAttribute("user", user);
            return "pages/admin/order-details";
        }
        return "redirect:/login";
    }
    @PostMapping("/orders/update/status")
    public String updateOrderStatus(@RequestParam(name = "ordersChecked") String[] ordersId){
        if(ordersId.length > 0){
            orderService.updateOrderStatus(ordersId);
        }
        return "redirect:/admin/orders";
    }
    @GetMapping("/orders/cancel")
    public String cancelOrder(@RequestParam(name = "id") String orderId){
        if(!orderId.isBlank()){
            orderService.cancelOrder(orderId);
        }
        return "redirect:/admin/orders";
    }
}
