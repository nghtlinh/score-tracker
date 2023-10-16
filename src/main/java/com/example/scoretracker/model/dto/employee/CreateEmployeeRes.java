package com.example.scoretracker.model.dto.employee;

import lombok.Data;

@Data
public class CreateEmployeeRes {

    private Long id;

    private String employeeName;

    private String email;

    private Long teamId;

    private String teamName;

    private String username;

    private String password;

    private String createdAt;

    private String updatedAt;
}
