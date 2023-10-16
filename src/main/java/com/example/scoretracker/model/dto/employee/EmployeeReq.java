package com.example.scoretracker.model.dto.employee;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeReq {

    private String employeeName;

    private String email;

    private Long teamId;

    private List<Long> roleIdList;

}
