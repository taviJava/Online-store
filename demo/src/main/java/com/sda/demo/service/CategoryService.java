package com.sda.demo.service;

import com.sda.demo.dto.CategoryDto;
import com.sda.demo.dto.ProductDto;
import com.sda.demo.persitance.model.CategoryModel;
import com.sda.demo.persitance.model.ProductModel;
import com.sda.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
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
             List<CategoryModel> subCategories = categoryModel.getSubcategories();
             List<CategoryDto> categoryDtos = new ArrayList<>();
             for (CategoryModel subCategory: subCategories){
                 CategoryDto subCategoryDto = new CategoryDto();
                 subCategoryDto.setId(subCategory.getId());
                 subCategoryDto.setName(subCategory.getName());
                 categoriesDto.add(subCategoryDto);
             }
             categoryDto.setSubcategories(categoryDtos);
             categoriesDto.add(categoryDto);
         }
         return categoriesDto;
    }

    private void add(CategoryDto categoryDto){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setName(categoryDto.getName());
        List<CategoryModel> subCategories = new ArrayList<>();
        for (CategoryDto subCategory: categoryDto.getSubcategories()){
            CategoryModel subCategoryModel = new CategoryModel();
            subCategoryModel.setId(subCategory.getId());
            subCategoryModel.setName(subCategory.getName());
            subCategories.add(subCategoryModel);
        }
        categoryModel.setSubcategories(subCategories);
    }
 public CategoryDto getCategory(long id){
CategoryModel categoryModel = categoryRepository.findById(id).orElse(null);
CategoryDto categoryDto = new CategoryDto();
categoryDto.setId(categoryModel.getId());
categoryDto.setName(categoryModel.getName());
         List<CategoryModel> subCategories = categoryModel.getSubcategories();
         List<CategoryDto> subCategoryDtos = new ArrayList<>();
         for (CategoryModel subCategory: subCategories){
             CategoryDto subCategoryDto = new CategoryDto();
             subCategoryDto.setId(subCategory.getId());
             subCategoryDto.setName(subCategory.getName());
             subCategoryDtos.add(subCategoryDto);
         }
         categoryDto.setSubcategories(subCategoryDtos);
         return categoryDto;
 }
public void delete(long id){
      categoryRepository.deleteById(id);
}

public void update(CategoryDto categoryDto){
        CategoryModel categoryModel = categoryRepository.findById(categoryDto.getId()).orElse(null);
    categoryModel.setName(categoryDto.getName());
    List<CategoryModel> subCategories = new ArrayList<>();
    for (CategoryDto subCategory: categoryDto.getSubcategories()){
        CategoryModel subCategoryModel = new CategoryModel();
        subCategoryModel.setId(subCategory.getId());
        subCategoryModel.setName(subCategory.getName());
        subCategories.add(subCategoryModel);
    }
    categoryModel.setSubcategories(subCategories);
}
}
