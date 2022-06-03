package com.example.deanery1.controller2;

import com.example.deanery1.model.Group;
import com.example.deanery1.service.CategoryService;
import com.example.deanery1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    UserService userService;



    @GetMapping("/create")
    public String createCategory(){
        return "AddCategoryForm";
    }

    @PostMapping("/create")
    public String createCategory(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("imageUrl") String imageUrl ){
        Group group = new Group(name, description, imageUrl);
        categoryService.save(group);
        return "redirect:/category/admin/list";
    }

    @GetMapping("/list")
    public String getList(Model model, HttpSession session) throws Exception {
        List<Group> groupList = categoryService.getAll();
        model.addAttribute("categoryList", groupList);
        return "index";

    }

    @GetMapping("/update/{categoryId}")
    public String updateCategory(@PathVariable("categoryId") int categoryId, Model model){
        if(!categoryService.findById(categoryId)){
            return "Group does not exist";
        }
        Group group = categoryService.getById(categoryId);
        model.addAttribute("category", group);
        return "UpdateCategoryForm";
    }

    @PostMapping("/update/{categoryId}")
    public String updateCategory(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("imageUrl") String imageUrl, @PathVariable("categoryId") int categoryId){
        Group group = new Group(categoryId, name, description, imageUrl);
        if(!categoryService.findById(categoryId)){
            return "Group does not exist";
        }
        System.out.println(group);
        categoryService.updateById(categoryId, group);
        return "redirect:/category/admin/list";
    }

    @GetMapping("admin/list")
    public String getAdminList(Model model, HttpSession session) throws Exception {
        List<Group> groupList = categoryService.getAll();
        model.addAttribute("categoryList", groupList);
        return "adminCategories";

    }

    @PostMapping("delete/{id}")
    public String deleteCategory(@PathVariable("id") int id){
        categoryService.deleteById(id);
        return "redirect:/category/admin/list";
    }
}
