package com.sda.demo.repository;

import com.sda.demo.persitance.model.CategoryModel;
import com.sda.demo.persitance.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    public Optional<OrderModel> findOrderModelByUserName(String username);
}

