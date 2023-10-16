package com.example.scoretracker.model.dto.tracking;

import lombok.Data;

@Data
public class TrackingReq {

    private Long employeeId;

    private Long faultId;

    private String note;

}
