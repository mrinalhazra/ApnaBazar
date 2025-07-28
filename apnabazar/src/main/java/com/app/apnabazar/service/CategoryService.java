package com.app.apnabazar.service;

import com.app.apnabazar.exception.AlreadyExists;
import com.app.apnabazar.exception.NotFoundException;
import com.app.apnabazar.model.Category;
import com.app.apnabazar.repository.CategoryRepository;
import com.app.apnabazar.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public void create(CategoryDto categoryDto) {
        if(categoryRepository.findByName(categoryDto.getName()) != null){
            throw new AlreadyExists("Category Already exists.");
        }
        Category category = new Category();
        category.setType(categoryDto.getType());
        category.setName(categoryDto.getName());

        categoryRepository.save(category);
    }

    public void update(Long id, CategoryDto updateCategoryDto) {
        if(getCategoryById(id) == null){
            throw new NotFoundException("Category not found for id: "+id);
        }
        Category category = getCategoryById(id);
        category.setName(updateCategoryDto.getName());
        category.setType(updateCategoryDto.getType());

        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Category does not exists.");
        }
    }
}
