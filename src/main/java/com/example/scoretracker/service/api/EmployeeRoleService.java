package com.example.scoretracker.service.api;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.dto.employeeRole.EmployeeRoleRes;
import com.example.scoretracker.model.dto.employeeRole.UpdateEmployeeRoleReq;
import com.example.scoretracker.model.entity.EmployeeEntity;
import com.example.scoretracker.model.entity.EmployeeRoleEntity;
import com.example.scoretracker.model.entity.RoleEntity;
import com.example.scoretracker.repository.EmployeeRepository;
import com.example.scoretracker.repository.EmployeeRoleRepository;
import com.example.scoretracker.repository.RoleRepository;
import com.example.scoretracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmployeeRoleService {

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeRoleRes updateEmployeeRoleById(Long id, UpdateEmployeeRoleReq newEmpRole) {
        EmployeeRoleEntity updatedEmployeeRole = employeeRoleRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found employee role with id[%s]", id)));

        EmployeeEntity employeeEntity = employeeRepository.findEmployeeEntitiesById(newEmpRole.getEmployeeId());
        RoleEntity roleEntity = roleRepository.findRoleEntitiesById(newEmpRole.getRoleId());
        updatedEmployeeRole.setEmployee(employeeEntity);
        updatedEmployeeRole.setRole(roleEntity);
        updatedEmployeeRole.setUpdatedBy(newEmpRole.getEditor());
        updatedEmployeeRole.setUpdatedAt(LocalDateTime.now());
        employeeRoleRepository.save(updatedEmployeeRole);

        return convertEmployeeRole(updatedEmployeeRole);
    }

    public EmployeeRoleRes convertEmployeeRole(EmployeeRoleEntity entity) {
        EmployeeRoleRes dto = new EmployeeRoleRes();

        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setRoleId(entity.getRole().getId());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setCreatedAt(CommonUtils.convertToString(entity.getCreatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));
        dto.setUpdatedAt(CommonUtils.convertToString(entity.getUpdatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));

        return dto;
    }


}
