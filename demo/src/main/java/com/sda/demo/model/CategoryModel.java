package com.sda.demo.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    private Long id;
    private String name;
    private CategoryModel parent;
    private List<CategoryModel> subcategories =  new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryModel getParent() {
        return parent;
    }

    public void setParent(CategoryModel parent) {
        this.parent = parent;
    }

    public List<CategoryModel> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryModel> subcategories) {
        this.subcategories = subcategories;
    }
}
