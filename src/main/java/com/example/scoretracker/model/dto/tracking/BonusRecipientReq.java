package com.example.scoretracker.model.dto.tracking;

import lombok.Data;

@Data
public class BonusRecipientReq {

    private Long employeeId1;

    private Long employeeId2;

    private Double score1;

    private Double score2;

    private int month;

    private int year;

}
