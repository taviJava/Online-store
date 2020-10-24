package com.sda.demo.repository;

import com.sda.demo.persitance.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
   List<OrderModel> findAllByUsername(String username);

}
