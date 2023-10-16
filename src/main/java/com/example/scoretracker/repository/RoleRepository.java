package com.example.scoretracker.repository;

import com.example.scoretracker.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findRoleEntitiesById(Long id);

}
