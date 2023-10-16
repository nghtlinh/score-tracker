package com.example.scoretracker.controller;

import com.example.scoretracker.model.common.ResponseDto;
import com.example.scoretracker.model.dto.employee.CreateEmployeeRes;
import com.example.scoretracker.model.dto.employee.EmployeeReq;
import com.example.scoretracker.model.dto.employee.EmployeeRes;
import com.example.scoretracker.service.api.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/new/createEmployee")
    public ResponseDto<CreateEmployeeRes> createEmployee(@RequestBody EmployeeReq req) {
        return new ResponseDto<>(employeeService.createEmployee(req));
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseDto<EmployeeRes> getEmployee(@PathVariable(name = "id") Long employeeId) {
        return new ResponseDto<>(employeeService.findEmployeeById(employeeId));
    }

    @GetMapping("/getAllEmployee/list")
    public ResponseDto<List<EmployeeRes>> getAllEmployee(){
        return new ResponseDto<>(employeeService.findAllEmployee());
    }

    @PutMapping("/edit/updateEmployee/{id}")
    public ResponseDto<EmployeeRes> updateEmployee(@PathVariable(name = "id") Long employeeId,
                                                   @RequestBody EmployeeReq req) {
        return new ResponseDto<>(employeeService.updateEmployeeById(employeeId, req));
    }

    @DeleteMapping("/delete/deleteEmployee/{id}")
    public void deleteEmployee(@PathVariable(name = "id") Long employeeId) {
        employeeService.deleteEmployeeById(employeeId);
    }

    @GetMapping("/getEmployeeByTeam/{id}")
    public ResponseDto<List<EmployeeRes>> getEmployeeByTeam(@PathVariable(name = "id") Long teamId){
        return new ResponseDto<>(employeeService.getEmployeeByTeam(teamId));
    }

}
