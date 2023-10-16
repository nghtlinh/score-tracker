package com.example.scoretracker.model.dto.employeeRole;

import lombok.Data;

@Data
public class EmployeeRoleRes {

    private Long employeeId;

    private Long roleId;

    private String createdBy;

    private String updatedBy;

    private String createdAt;

    private String updatedAt;
}
