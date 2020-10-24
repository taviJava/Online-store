package com.sda.demo.service;

import com.sda.demo.persitance.dto.ProductDto;
import com.sda.demo.persitance.dto.ReviewDto;
import com.sda.demo.persitance.model.ProductModel;
import com.sda.demo.persitance.model.ReviewModel;
import com.sda.demo.repository.ProductRepository;
import com.sda.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
    public class ReviewService {
        @Autowired
        private ReviewRepository reviewRepository;
        @Autowired
        private ProductRepository productRepository;
        public List<ReviewDto> getAll() {
            List<ReviewModel> reviewModels = reviewRepository.findAll();
            List<ReviewDto> reviewDto = new ArrayList<>();
            for (ReviewModel reviewModel : reviewModels) {
                ReviewDto reviewDto1 = new ReviewDto();
                reviewDto1.setId(reviewModel.getId());
                reviewDto1.setComment(reviewModel.getComment());
                reviewDto1.setRating(reviewModel.getRating());
                reviewDto.add(reviewDto1);
            }
            return reviewDto;
        }
        public void add(ReviewDto reviewDto) {
            ReviewModel reviewModel = new ReviewModel();
            reviewModel.setComment(reviewDto.getComment());
            reviewModel.setRating(reviewDto.getRating());
            ProductDto productDto = reviewDto.getProductDto();
            if(productDto != null){
                ProductModel productModel = productRepository.findById(productDto.getId()).orElse(null);
                reviewModel.setProduct(productModel);
            }reviewRepository.save(reviewModel);
        }
        public ReviewDto getReview(long id) {
            ReviewModel reviewModel = reviewRepository.findById(id).orElse(null);
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setId(reviewModel.getId());
            reviewDto.setComment(reviewModel.getComment());
            reviewDto.setRating(reviewModel.getRating());
            reviewModel.setProduct(reviewModel.getProduct());
            return reviewDto;
        }
        public void delete(long id) {
            reviewRepository.deleteById(id);
        }
        public void update(ReviewDto reviewDto) {
            ReviewModel reviewModel = reviewRepository.findById(reviewDto.getId()).orElse(null);
            reviewModel.setId(reviewDto.getId());
            reviewModel.setRating(reviewDto.getRating());
            reviewModel.setComment(reviewDto.getComment());
            ProductModel productModel =new ProductModel();
            ProductDto productDto=reviewDto.getProductDto();
            productModel.setName(productDto.getName());
            reviewRepository.save(reviewModel);
        }
    }

