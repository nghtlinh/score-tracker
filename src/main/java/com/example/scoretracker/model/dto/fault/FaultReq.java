package com.example.scoretracker.model.dto.fault;

import lombok.Data;

@Data
public class FaultReq {

    private String faultName;

    private double defaultScore;

}
