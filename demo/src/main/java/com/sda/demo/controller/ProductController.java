package com.sda.demo.controller;

import com.sda.demo.dto.ProductDto;
import com.sda.demo.dto.UserDto;
import com.sda.demo.persitance.model.ProductModel;
import com.sda.demo.repository.ProductRepository;
import com.sda.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/product")
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        productService.delete(id);
    }
    @GetMapping("/product")
    public List<ProductDto> getProduct() {
        return productService.getProducts();
    }
    @GetMapping("/product/{id}")
    public ProductDto getProduct(@PathVariable(name = "id") Long id) {
        return productService.getProduct(id);
    }
    @PutMapping("/product")
    public void update(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }
}
