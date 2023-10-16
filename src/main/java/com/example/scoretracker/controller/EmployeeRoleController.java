package com.example.scoretracker.controller;

import com.example.scoretracker.model.common.ResponseDto;
import com.example.scoretracker.model.dto.employeeRole.EmployeeRoleRes;
import com.example.scoretracker.model.dto.employeeRole.UpdateEmployeeRoleReq;
import com.example.scoretracker.service.api.EmployeeRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employeeRole")
public class EmployeeRoleController {

    @Autowired
    private EmployeeRoleService employeeRoleService;

    @PutMapping("/edit/updateEmployeeRole/{id}")
    public ResponseDto<EmployeeRoleRes> updateEmployeeRole(@PathVariable(name = "id") Long id,
                                                           @RequestBody UpdateEmployeeRoleReq req) {
        return new ResponseDto<>(employeeRoleService.updateEmployeeRoleById(id, req));
    }


}
