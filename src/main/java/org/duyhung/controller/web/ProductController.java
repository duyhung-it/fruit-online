package org.duyhung.controller.web;

import org.duyhung.entity.CartDetail;
import org.duyhung.entity.Product;
import org.duyhung.service.CategoryService;
import org.duyhung.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("ProductWebController")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/san-pham")
    public String getProductPage(Model model, @RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "6") Integer size,
                                 @RequestParam(name = "category_id", required = false) String categoryId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> page1 = null;
        if (categoryId != null && !categoryId.isEmpty()) {
            page1 = productService.findAllByCategory_Id(categoryId, pageable);
            model.addAttribute("categoryId",categoryId);
        } else {
            page1 = productService.getAllProducts(pageable);
        }
        model.addAttribute("listCategories", categoryService.getAllCategories());
        model.addAttribute("list", page1.getContent());
        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("currentPage",page);
        return "pages/web/san-pham";
    }

    @GetMapping("/san-pham/chi-tiet")
    public String getProductDetailsPage( Model model, @RequestParam String id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("cartDetails", new CartDetail());
        return "pages/web/chi-tiet-san-pham";
    }
}
