package com.example.scoretracker.repository;

import com.example.scoretracker.model.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAllByTeamId(Long teamId);

    EmployeeEntity findEmployeeEntitiesById(Long id);

    @Query("select e.id from EmployeeEntity as e")
    List<Long> findAllIds();

    EmployeeEntity findFirstByUsername(String username);

    EmployeeEntity findFirstByEmail(String email);
}
