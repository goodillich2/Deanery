package com.example.deanery1.repository;

import com.example.deanery1.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Group, Integer> {
}
