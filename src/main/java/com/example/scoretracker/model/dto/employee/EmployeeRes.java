package com.example.scoretracker.model.dto.employee;

import lombok.Data;

@Data
public class EmployeeRes {

    private Long id;

    private String employeeName;

    private String email;

    private Long teamId;

    private String teamName;

    private String username;

    private String createdAt;

    private String updatedAt;
}
