package com.example.scoretracker.repository;

import com.example.scoretracker.model.entity.FaultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FaultRepository extends JpaRepository<FaultEntity, Long> {

    @Query("select f.defaultScore from FaultEntity as f where f.id = :faultId")
    double findDefaultScoreByFaultId(Long faultId);

}
