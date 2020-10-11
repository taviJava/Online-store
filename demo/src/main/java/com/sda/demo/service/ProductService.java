package com.sda.demo.service;

import com.sda.demo.dto.CategoryDto;
import com.sda.demo.dto.ManufacturerDto;
import com.sda.demo.dto.ProductDto;
import com.sda.demo.persitance.model.ProductModel;
import com.sda.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    @Autowired
private ProductRepository productRepository;

    public List<ProductDto> getProducts(){
        List<ProductModel> productModels = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductModel productModel: productModels){
            ProductDto productDto = new ProductDto();
            productDto.setId(productModel.getId());
            productDto.setPrice(productModel.getPrice());
            productDto.setName(productModel.getName());
            productDto.setPhoto(productModel.getPhoto());
            productDto.setDescription(productModel.getDescription());
            CategoryDto categoryDto =new CategoryDto();
            categoryDto.setId(productModel.getId());
            categoryDto.setName(productModel.getName());
            productDto.setCategory(categoryDto);
            ManufacturerDto manufacturerDto = new ManufacturerDto();
            manufacturerDto.setId(productModel.getManufacturer().getId());
            manufacturerDto.setName(productModel.getManufacturer().getName());
            productDto.setManufacturer(manufacturerDto);
            productDto.setProductType(productModel.getProductType().name());
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
}
