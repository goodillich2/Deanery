package com.example.deanery1.controller2;


import com.example.deanery1.Dto.ProductDto;
import com.example.deanery1.model.Group;
import com.example.deanery1.model.Student;
import com.example.deanery1.repository.CategoryRepository;
import com.example.deanery1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepository categoryRepo;

    @GetMapping("/add")
    public String  createProduct() {
        return "AddProductForm";
    }

    @PostMapping("/add")
    public String  createProduct(@RequestParam("name") String name, @RequestParam("description") String description,
                                 @RequestParam("price") int price, @RequestParam("imageUrl") String imageURL,
                                 @RequestParam("categoryId") int categoryId) {
        ProductDto productDto = new ProductDto(name, imageURL, price, description,categoryId);

        Optional<Group> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return "category does not exists";
        }
        productService.createProduct(productDto, optionalCategory.get());
        return "redirect:/product/list";
    }

    @GetMapping("/{categoryId}")
    public String  getProductsFromOneCategory(@PathVariable int categoryId, Model model) {
        List<ProductDto> products = productService.getAllProductsFromOneCategory(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);
        System.out.println(products);
        return "products";
    }

    // create an api to edit the product

    @GetMapping("/update/{productId}")
    public String  updateProduct(@PathVariable("productId") int productId, Model model) throws Exception {
        Student student = productService.findById(productId);
        /*Optional<Group> optionalCategory = categoryRepo.findById(productId);
        if (!optionalCategory.isPresent()) {
            return "category does not exist";
        }*/
        model.addAttribute("product", student);
        return "UpdateProductForm";
    }


    @PostMapping("/update/{productId}")
    public String  updateProduct(@RequestParam("name") String name, @RequestParam("description") String description,
                                 @RequestParam("price") int price, @RequestParam("imageUrl") String imageURL, @RequestParam("id") int id, @RequestParam("categoryId") int categoryId,
                                 @PathVariable("productId") int productId) throws Exception {
        ProductDto productDto = new ProductDto(id, name, imageURL, price, description,categoryId);
        Optional<Group> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return "category does not exist";
        }
        System.out.println(productDto);
        productService.updateProduct(productDto, productId);
        return "redirect:/product/list";
        }

    @GetMapping("/list")
    public String  getAllProducts(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "adminProducts";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }

}


