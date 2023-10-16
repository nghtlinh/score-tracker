package com.example.scoretracker.model.dto.fault;

import lombok.Data;

@Data
public class FaultRes {

    private Long id;

    private String faultName;

    private double defaultScore;

    private String createdAt;

    private String updatedAt;
}
