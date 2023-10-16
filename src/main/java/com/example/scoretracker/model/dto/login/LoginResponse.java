package com.example.scoretracker.model.dto.login;

import com.example.scoretracker.model.dto.employee.EmployeeRes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String authorization;

    private EmployeeRes employeeRes;

    public LoginResponse(String authorization, EmployeeRes employeeRes) {
        this.authorization = authorization;
        this.employeeRes = employeeRes;
    }

}
