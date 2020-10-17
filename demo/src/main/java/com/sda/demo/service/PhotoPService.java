package com.sda.demo.service;


import com.sda.demo.persitance.model.PhotoPModel;
import com.sda.demo.persitance.model.ProductModel;
import com.sda.demo.repository.PhotoPRepository;
import com.sda.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class PhotoPService {

    @Autowired
    private PhotoPRepository photoPRepository;

    @Autowired
    private ProductRepository productRepository;

    public PhotoPModel store(MultipartFile file) throws IOException, InterruptedException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        PhotoPModel photoP = new PhotoPModel(fileName, file.getContentType(), file.getBytes());
        TimeUnit.SECONDS.sleep(3);
        List<ProductModel> products = productRepository.findAll();
        ProductModel productModel = products.get(products.size()-1);
        photoP.setProduct(productModel);
        return photoPRepository.save(photoP);
    }

    public PhotoPModel getPhoto(String id) {
        return photoPRepository.findById(id).get();
    }


    public Stream<PhotoPModel> getProductPhoto(long id) {
        ProductModel productModel = productRepository.findById(id).orElse(null);
        List<PhotoPModel> list = new ArrayList<>();
        list.add(productModel.getPhotos());
        return list.stream();
    }

}
