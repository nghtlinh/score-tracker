package com.example.scoretracker.repository;

import com.example.scoretracker.model.entity.EmployeeEntity;
import com.example.scoretracker.model.entity.EmployeeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRoleEntity, Long> {

    @Query
    List<EmployeeRoleEntity> findAllByEmployee(EmployeeEntity employee);
}
