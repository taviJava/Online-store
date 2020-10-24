package com.sda.demo.repository;


import com.sda.demo.persitance.model.OrderLineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineModel,Long> {

}
