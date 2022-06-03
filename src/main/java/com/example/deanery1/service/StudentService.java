package com.example.deanery1.service;

import com.example.deanery1.Dto.ProductDto;
import com.example.deanery1.model.Group;
import com.example.deanery1.model.Student;
import com.example.deanery1.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void createProduct(ProductDto productDto, Group group) {
        Student student = new Student();
        student.setName(productDto.getName());
        student.setSurname(productDto.getSurname());
        student.setImageURL(productDto.getImageURL());
        student.setGroup(group);
        student.setPersonalInfo(productDto.getPersonalInfo());
        studentRepository.save(student);
    }

    public ProductDto getProductDto(Student student) {
        ProductDto productDto = new ProductDto();
        productDto.setSurname(student.getSurname());
        productDto.setImageURL(student.getImageURL());
        productDto.setName(student.getName());
        productDto.setGroupId(student.getGroup().getId());
        productDto.setPersonalInfo(student.getPersonalInfo());
        productDto.setId(student.getId());
        return productDto;
    }

    public List<ProductDto> getAllProducts() {
        List<Student> allStudents = studentRepository.findAll();

        List<ProductDto> productDtos = new ArrayList<>();
        for(Student student : allStudents) {
            productDtos.add(getProductDto(student));
        }
        return productDtos;
    }

    public void updateProduct(ProductDto productDto, int productId) throws Exception {
        Optional<Student> optionalProduct = studentRepository.findById(productId);
        // throw an exception if student does not exists
        if (!optionalProduct.isPresent()) {
            throw new Exception("student not present");
        }
        Student student = optionalProduct.get();
        student.setPersonalInfo(productDto.getPersonalInfo());
        student.setImageURL(productDto.getImageURL());
        student.setName(productDto.getName());
        student.setSurname(productDto.getSurname());
        studentRepository.save(student);
    }

    public Student findById(Integer productId) throws Exception {
        Optional<Student> optionalProduct =  studentRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new Exception("student does not exist");
        }
        Student student = optionalProduct.get();
        return student;
    }

    public List<ProductDto> getAllProductsFromOneCategory(int category_id) {
        List<Student> allStudents = studentRepository.getAllByGroup_Id(category_id);

        List<ProductDto> productDtos = new ArrayList<>();
        for(Student student : allStudents) {
            productDtos.add(getProductDto(student));
        }
        return productDtos;
    }

    public void deleteProduct(int id){
        studentRepository.deleteById(id);
    }


}
