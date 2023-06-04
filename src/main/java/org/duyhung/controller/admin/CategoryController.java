package org.duyhung.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.duyhung.entity.Category;
import org.duyhung.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.List;

@Controller("CategoryAdminController")
@RequestMapping("/admin/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String getCategoriesAdminPage(Model model,@RequestParam String action,@RequestParam(required = false) String id){
        String content = "";
        if( action.equalsIgnoreCase("add")){
            content = "form-categories";
            model.addAttribute("button","Add Category");
            model.addAttribute("category",new Category());
        } else if (action.equalsIgnoreCase("update")) {
            content = "form-categories";
            Category category = categoryService.getCategoryById(id);
            model.addAttribute("category",category);
            model.addAttribute("button","Update Category");
        }else{
            List<Category> list = categoryService.getAllCategories();
            content = "list-categories";
            model.addAttribute("list",list);
        }
        model.addAttribute("content",content);
        return "pages/admin/index";
    }

    @PostMapping("/save")
    public String saveCategory(@Valid @ModelAttribute(name = "category") Category category, Errors errors, Model model, HttpServletRequest request){
        if(errors.hasErrors()){
            model.addAttribute("content","form-categories");
            if(category.getId().isEmpty()){
                model.addAttribute("button","Add");
            }else {
                model.addAttribute("button","Update");
            }
            return "pages/admin/index";
        }
        categoryService.saveCategory(category);
        return "redirect:/admin/categories?action='list'";
    }
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam("id") String id,RedirectAttributes redirectAttributes){
        categoryService.deleteCategory(id);
        redirectAttributes.addAttribute("message","Category deleted successfully");
        return "redirect:/admin/categories?action='list'";
    }
}
