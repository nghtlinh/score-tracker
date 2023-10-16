package com.example.scoretracker.model.dto.tracking;

import com.example.scoretracker.model.dto.employee.EmployeeRes;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EmployeeScoreRes {

    private EmployeeRes employeeRes;

    private Map<Integer, Double> employeeScoreList;

}
