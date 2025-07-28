package com.app.apnabazar.controller;

import com.app.apnabazar.model.Category;
import com.app.apnabazar.service.CategoryService;
import com.app.apnabazar.dto.CategoryDto;
import com.app.apnabazar.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category getCategory(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage createCategory(@RequestBody CategoryDto categoryDto){
        categoryService.create(categoryDto);
        return new ResponseMessage("Category created successfully.");
    }

    @PutMapping("/update/{id}")
    public ResponseMessage updateCategory(@PathVariable Long id, @RequestBody CategoryDto updateCategoryDto){
        categoryService.update(id, updateCategoryDto);
        return new ResponseMessage("Category updated successfully.");
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseMessage deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseMessage("Category deleted successfully.");
    }
}
