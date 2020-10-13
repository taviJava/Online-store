package com.sda.demo.controller;

import com.sda.demo.dto.CategoryDto;
import com.sda.demo.dto.UserDto;
import com.sda.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping("/category")
    public void addCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.add(categoryDto);
    }
    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable(name = "id") Long id) {
        categoryService.delete(id);
    }
    @GetMapping("/category")
    public List<CategoryDto> getCategory() {
        return categoryService.getCategories();
    }
    @GetMapping("/categorySub")
    public List<CategoryDto> getSubCategory() {
        return categoryService.getSubCategories();
    }
    @GetMapping("/category/{id}")
    public CategoryDto getCategory(@PathVariable(name = "id") Long id) {
        return categoryService.getCategory(id);
    }

    @PutMapping("/category")
    public void update(@RequestBody CategoryDto categoryDto) {
        categoryService.update(categoryDto);

    }
}
