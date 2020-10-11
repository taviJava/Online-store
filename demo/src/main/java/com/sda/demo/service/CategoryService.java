package com.sda.demo.service;

import com.sda.demo.dto.CategoryDto;
import com.sda.demo.dto.ProductDto;
import com.sda.demo.persitance.model.CategoryModel;
import com.sda.demo.persitance.model.ProductModel;
import com.sda.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private List<CategoryDto> getCategories(){
        List<CategoryModel> categories = categoryRepository.findAll();
         List<CategoryDto> categoriesDto = new ArrayList<>();
         for (CategoryModel categoryModel: categories){
             CategoryDto categoryDto = new CategoryDto();
             categoryDto.setId(categoryModel.getId());
             categoryDto.setName(categoryModel.getName());
            
             categoriesDto.add(categoryDto);
         }
         return categoriesDto;
    }
}
