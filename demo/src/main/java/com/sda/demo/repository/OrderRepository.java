package com.sda.demo.repository;

import com.sda.demo.persitance.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<CategoryModel, Long> {
}

