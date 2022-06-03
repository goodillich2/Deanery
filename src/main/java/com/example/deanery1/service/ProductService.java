package com.example.deanery1.service;

import com.example.deanery1.Dto.ProductDto;
import com.example.deanery1.model.Group;
import com.example.deanery1.model.Student;
import com.example.deanery1.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;

    public void createProduct(ProductDto productDto, Group group) {
        Student student = new Student();
        student.setName(productDto.getName());
        student.setPrice(productDto.getPrice());
        student.setImageURL(productDto.getImageURL());
        student.setCategory(group);
        student.setDescription(productDto.getDescription());
        productRepository.save(student);
    }

    public ProductDto getProductDto(Student student) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(student.getDescription());
        productDto.setImageURL(student.getImageURL());
        productDto.setName(student.getName());
        productDto.setCategoryId(student.getCategory().getId());
        productDto.setPrice(student.getPrice());
        productDto.setId(student.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Student> allStudents = productRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for(Student student : allStudents) {
            productDtos.add(getProductDto(student));
        }
        return productDtos;
    }

    public void updateProduct(ProductDto productDto, int productId) throws Exception {
        Optional<Student> optionalProduct = productRepository.findById(productId);
        // throw an exception if student does not exists
        if (!optionalProduct.isPresent()) {
            throw new Exception("student not present");
        }
        Student student = optionalProduct.get();
        student.setDescription(productDto.getDescription());
        student.setImageURL(productDto.getImageURL());
        student.setName(productDto.getName());
        student.setPrice(productDto.getPrice());
        productRepository.save(student);
    }

    public Student findById(Integer productId) throws Exception {
        Optional<Student> optionalProduct =  productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new Exception("student does not exist");
        }
        Student student = optionalProduct.get();
        return student;
    }

    public List<ProductDto> getAllProductsFromOneCategory(int category_id) {
        List<Student> allStudents = productRepository.getAllByGroup_Id(category_id);

        List<ProductDto> productDtos = new ArrayList<>();
        for(Student student : allStudents) {
            productDtos.add(getProductDto(student));
        }
        return productDtos;
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }


}
