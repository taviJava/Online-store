package com.sda.demo.repository;


import com.sda.demo.persitance.model.PrivilegeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeModel,Long> {

}
