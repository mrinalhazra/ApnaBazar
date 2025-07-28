package com.app.apnabazar.repository;

import com.app.apnabazar.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
