package com.sda.demo.repository;

import com.sda.demo.persitance.model.OrderLineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineModel,Long> {
}
