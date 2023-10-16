package com.example.scoretracker.model.dto.tracking;

import lombok.Data;

@Data
public class TrackingRes {

    private Long id;

    private Long employeeId;

    private String employeeName;

    private String teamName;

    private Long faultId;

    private String faultName;

    private String note;

    private String createdAt;

    private String updatedAt;
}
