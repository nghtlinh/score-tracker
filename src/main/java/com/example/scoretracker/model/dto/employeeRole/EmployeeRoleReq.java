package com.example.scoretracker.model.dto.employeeRole;

import lombok.Data;

@Data
public class EmployeeRoleReq {

    private Long employeeId;

    private Long roleId;

    private String creator;

}
