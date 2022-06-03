package com.example.deanery1.service;


import com.example.deanery1.model.Group;
import com.example.deanery1.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Group> getAll(){return categoryRepository.findAll();}
    public Group getById(int id){
        return categoryRepository.getById(id);
    }
    public Group save(Group group){
        return categoryRepository.save(group);
    }
    public void deleteById(int id){
        categoryRepository.deleteById(id);
    }

    public void updateById(int id, Group group){
        Group group1 = categoryRepository.getById(id);
        group1.setId(group.getId());
        group1.setName(group.getName());
        group1.setDescription(group.getDescription());
        group1.setImageUrl(group.getImageUrl());
        categoryRepository.save(group1);

    }


    public boolean findById(int categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }
}
