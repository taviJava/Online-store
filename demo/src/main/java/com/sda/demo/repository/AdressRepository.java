package com.sda.demo.repository;

import com.sda.demo.persitance.model.AdressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends JpaRepository<AdressModel,Long> {
}
