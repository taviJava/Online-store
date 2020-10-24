package com.sda.demo.repository;

import com.sda.demo.persitance.model.PhotoP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoPRepository extends JpaRepository<PhotoP,String> {
}
