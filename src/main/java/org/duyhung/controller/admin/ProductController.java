package org.duyhung.controller.admin;

import jakarta.validation.Valid;
import org.duyhung.entity.Product;
import org.duyhung.service.CategoryService;
import org.duyhung.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller("ProductAdminController")
@RequestMapping("/admin/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    public String getProductsAdminPage(Model model,@RequestParam String action,
                                       @RequestParam(required = false) String id,
                                       @RequestParam(required = false,defaultValue = "1") Integer page,
                                       @RequestParam(required = false,defaultValue = "5") Integer size){
        String content = "";
        if( action.equalsIgnoreCase("add")){
            content = "form-products";
            model.addAttribute("listCategories",categoryService.getAllCategories());
            model.addAttribute("button","Add Product");
            model.addAttribute("product",new Product());
        } else if (action.equalsIgnoreCase("update")) {
            content = "form-products";
            Product product = productService.getProductById(id);
            model.addAttribute("listCategories",categoryService.getAllCategories());
            model.addAttribute("button","Update Product");
            model.addAttribute("product",product);
        }else{
            Page<Product> pageProducts = productService.getAllProducts(PageRequest.of(page-1,size));
            List<Product> list = pageProducts.getContent();
            model.addAttribute("totalPages",pageProducts.getTotalPages());
            model.addAttribute("list",list);
            content = "list-products";
        }
        model.addAttribute("content",content);
        return "pages/admin/index";
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute(name = "product") Product product, Errors errors, Model model, RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            model.addAttribute("content","form-products");
            model.addAttribute("listCategories",categoryService.getAllCategories());
            if(product.getId().isEmpty()){
                model.addAttribute("button","Add Product");
            }else {
                model.addAttribute("button","Update Product");
            }
            return "pages/admin/index";
        }
        productService.saveProduct(product);
        redirectAttributes.addAttribute("message","Product added successfully");
        return "redirect:/admin/products?action='list'";
    }
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam("id") String id, RedirectAttributes redirectAttributes){
        productService.deleteProduct(id);
        redirectAttributes.addAttribute("message","Product deleted successfully");
        return "redirect:/admin/products?action='list'";
    }
}
