package com.example.scoretracker.repository;

import com.example.scoretracker.model.entity.TrackingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingEntity, Long> {

    @Query("select t from TrackingEntity as t where t.employeeId = :employeeId and month(t.createdAt) = :month and year(t.createdAt) = :year")
    List<TrackingEntity> findAllByEmployeeIdAndCreatedAtMonthYear(@Param("employeeId") Long employeeId, @Param("month") int month, @Param("year") int year);

    @Query("select t from TrackingEntity as t where month(t.createdAt) = :month and year(t.createdAt) = :year")
    List<TrackingEntity> getTrackingEntitiesByMonth(@Param("month") int month, @Param("year") int year);

    @Query("select distinct t.employeeId from TrackingEntity as t where t.faultId = :faultId and month(t.createdAt) = :month and year(t.createdAt) = :year")
    List<Long> getDistinctEmployeeIdByFaultId(@Param("faultId") Long faultId, @Param("month") int month, @Param("year") int year);


}
