package com.sda.demo.repository;

import com.sda.demo.persitance.model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel,Long> {
}
