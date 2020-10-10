package com.sda.demo.repository;

import com.sda.demo.persitance.model.PrivilegeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeModel,Long> {
}
