package com.sda.demo.repository;

import com.sda.demo.persitance.model.PhotoU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoURepository extends JpaRepository<PhotoU, String> {
}