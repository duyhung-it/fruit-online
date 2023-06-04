package org.duyhung.controller.web;

import jakarta.servlet.http.HttpSession;
import org.duyhung.entity.*;
import org.duyhung.service.CartDetailService;
import org.duyhung.service.CartService;
import org.duyhung.service.OrderService;
import org.duyhung.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class CartController {
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;
    private final CartDetailService cartDetailService;

    public CartController(UserService userService, CartService cartService, OrderService orderService, CartDetailService cartDetailService) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.cartDetailService = cartDetailService;
    }

    @GetMapping("/gio-hang")
    public String getCartPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("content", "gio-hang");
            User user = userService.getUserByEmail(authentication.getName());
            Cart cart = cartService.getCartsByUser(user);
            if (cart != null) {
                model.addAttribute("list", cart.getCartDetails());
            } else {
                cart = new Cart();
                cart.setUser(user);
                cart = cartService.saveCart(cart);
                model.addAttribute("list", cart.getCartDetails());
            }
            Double grandTotal = cartDetailService.getGrandTotal(cart);
            model.addAttribute("grandTotal", grandTotal);
            model.addAttribute("cart", cart);
            return "pages/web/index";
        }
        return "redirect:/login";
    }

    @GetMapping("/gio-hang/thanh-toan")
    public String getCartCheckoutPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("content", "thanh-toan");
            User user = userService.getUserByEmail(authentication.getName());
            Cart cart = cartService.getCartsByUser(user);
            if (cart.getCartDetails().isEmpty()) {
                return "redirect:/gio-hang";
            }
            Double grandTotal = cartDetailService.getGrandTotal(cart);
            model.addAttribute("grandTotal", grandTotal);
            model.addAttribute("cart", cart);
            model.addAttribute("user", user);
            return "pages/web/index";
        }
        return "redirect:/login";
    }

    @PostMapping("/gio-hang")
    public String addProductToCart(@ModelAttribute(name = "cartDetails") CartDetail cartDetail, Authentication authentication, HttpSession session) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.getUserByEmail(authentication.getName());
            Cart cart = cartService.getCartsByUser(user);
            CartDetail cartDetail1 = cartDetailService.getCartDetailById(new CartDetailId(cart, cartDetail.getId().getProduct()));
            if (cartDetail1 != null) {
                cartDetail1.setQuantity(cartDetail1.getQuantity() + cartDetail.getQuantity());
            } else {
                cartDetail1 = cartDetail;
                cartDetail1.getId().setCart(cart);
            }
            cartDetailService.saveCartDetail(cartDetail1);
            int NumberOfProduct = cartService.getCartById(cart.getId()).getCartDetails().size();
            session.setAttribute("cartProducts", NumberOfProduct);
            return "redirect:/san-pham/chi-tiet?id=" + cartDetail.getId().getProduct().getId();
        }
        return "redirect:/login";
    }

    @PostMapping("/gio-hang/update")
    public String updateCart(@ModelAttribute(name = "cartDetails") CartDetail cartDetails, RedirectAttributes attribute) {
        if (cartDetails != null) {
            cartDetailService.saveCartDetail(cartDetails);
            return "redirect:/gio-hang";
        }
        attribute.addAttribute("message", "failed");
        return "redirect:/gio-hang";
    }

    @GetMapping("/gio-hang/delete")
    public String deleteProductFromCart(@RequestParam String productId, @RequestParam String cartId, RedirectAttributes attribute, HttpSession session) {
        Product product = new Product();
        product.setId(productId);
        Cart cart = new Cart();
        cart.setId(cartId);
        CartDetailId cartDetailId = new CartDetailId(cart, product);
        cartDetailService.deleteCartDetail(cartDetailId);
        attribute.addAttribute("message", "success");
        int numberOfProducts = cartService.getCartById(cart.getId()).getCartDetails().size();
        if (numberOfProducts == 0) {
            session.removeAttribute("cartProducts");
        } else {
            session.setAttribute("cartProducts", numberOfProducts);
        }
        return "redirect:/gio-hang";
    }

    @GetMapping("/gio-hang/checkout")
    public String checkoutProduct(Authentication authentication, HttpSession session) {
        User user = userService.getUserByEmail(authentication.getName());
        Cart cart = cartService.getCartsByUser(user);
        if (cart.getCartDetails().size() > 0) {
            Order order = Order.builder()
                    .code(UUID.randomUUID().toString())
                    .status("1")
                    .totalPrice(cartDetailService.getGrandTotal(cart))
                    .user(user)
                    .build();
            Order finalOrder = order;
            List<OrderDetail> orderDetails = cart.getCartDetails().stream().map(mapper -> {
                return OrderDetail.builder()
                        .id(new OrderDetailId(finalOrder, mapper.getId().getProduct()))
                        .quantity(mapper.getQuantity())
                        .price(mapper.getId().getProduct().getPrice())
                        .build();
            }).toList();
            order.setOrderDetails(orderDetails);
            order = orderService.saveOrder(order);
            if (order != null) {
                cartService.deleteCart(cart);
            }
            session.removeAttribute("cartProducts");
        }
        return "redirect:/gio-hang";
    }
}
