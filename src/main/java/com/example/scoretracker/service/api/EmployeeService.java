package com.example.scoretracker.service.api;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.dto.employee.CreateEmployeeRes;
import com.example.scoretracker.model.dto.employee.EmployeeReq;
import com.example.scoretracker.model.dto.employee.EmployeeRes;
import com.example.scoretracker.model.dto.team.TeamRes;
import com.example.scoretracker.model.entity.EmployeeEntity;
import com.example.scoretracker.model.entity.EmployeeRoleEntity;
import com.example.scoretracker.model.entity.RoleEntity;
import com.example.scoretracker.repository.EmployeeRepository;
import com.example.scoretracker.repository.EmployeeRoleRepository;
import com.example.scoretracker.repository.RoleRepository;
import com.example.scoretracker.service.login.CustomUserDetails;
import com.example.scoretracker.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    public CreateEmployeeRes createEmployee (EmployeeReq req) {

        if (StringUtils.isEmpty(req.getEmployeeName())) {
            throw new IllegalArgumentException("employeeName cannot be empty");
        }

        EmployeeEntity employee = employeeRepository.findFirstByEmail(req.getEmail());

        if (!CommonUtils.validateEmail(req.getEmail())) {
            throw new AppException(new ErrorResponse("email invalid"));
        }

        if (employee != null) {
            throw new AppException(new ErrorResponse("email already existed"));
        }

        teamService.validateTeamId(req.getTeamId());

        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setEmployeeName(req.getEmployeeName());
        employeeEntity.setEmail(req.getEmail());
        employeeEntity.setTeamId(req.getTeamId());

        String username = CommonUtils.extractUsernameFromEmail(req.getEmail());
        employeeEntity.setUsername(username);

        String rawPassword = CommonUtils.alphaNumericString(10);
        employeeEntity.setPassword(CommonUtils.encodeRandomPassword(rawPassword, passwordEncoder));

        employeeEntity.setCreatedAt(LocalDateTime.now());
        employeeEntity.setUpdatedAt(LocalDateTime.now());
        employeeRepository.save(employeeEntity);

        createEmployeeRole(employeeEntity, req.getRoleIdList());

        return this.convertToCreateEmployeeRes(employeeEntity, rawPassword);
    }

    public CreateEmployeeRes convertToCreateEmployeeRes(EmployeeEntity entity, String rawPassword) {
        CreateEmployeeRes dto = new CreateEmployeeRes();

        dto.setId(entity.getId());
        dto.setEmployeeName(entity.getEmployeeName());
        dto.setEmail(entity.getEmail());
        dto.setTeamId(entity.getTeamId());

        TeamRes teamRes = teamService.findTeamById(entity.getTeamId());
        dto.setTeamName(teamRes.getTeamName());

        dto.setUsername(entity.getUsername());
        dto.setPassword(rawPassword);
        dto.setCreatedAt(CommonUtils.convertToString(entity.getCreatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));
        dto.setUpdatedAt(CommonUtils.convertToString(entity.getUpdatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));

        return dto;
    }

    public EmployeeRes convertToEmployeeRes(EmployeeEntity entity) {
        EmployeeRes dto = new EmployeeRes();

        dto.setId(entity.getId());
        dto.setEmployeeName(entity.getEmployeeName());
        dto.setEmail(entity.getEmail());
        dto.setTeamId(entity.getTeamId());

        TeamRes teamRes = teamService.findTeamById(entity.getTeamId());
        dto.setTeamName(teamRes.getTeamName());

        dto.setUsername(entity.getUsername());
        dto.setCreatedAt(CommonUtils.convertToString(entity.getCreatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));
        dto.setUpdatedAt(CommonUtils.convertToString(entity.getUpdatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));

        return dto;
    }

    public List<EmployeeRes> convertToListEmployeeRes(List<EmployeeEntity> entities){
        return entities.stream().map(this::convertToEmployeeRes).toList();
    }

    public EmployeeRes findEmployeeById(Long id){
        EmployeeEntity employeeEntity = employeeRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found employee with id[%s]", id)));
        return convertToEmployeeRes(employeeEntity);
    }

    public List<EmployeeRes> findAllEmployee() {
        List<EmployeeEntity> entities = employeeRepository.findAll();
        return convertToListEmployeeRes(entities);
    }

    public EmployeeRes updateEmployeeById (Long id, EmployeeReq newEmployee){
        teamService.validateTeamId(newEmployee.getTeamId());

        EmployeeEntity updatedEmployee = employeeRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found employee with id[%s]", id)));

        updatedEmployee.setEmployeeName(newEmployee.getEmployeeName());
        updatedEmployee.setEmail(newEmployee.getEmail());
        updatedEmployee.setTeamId(newEmployee.getTeamId());
        updatedEmployee.setUpdatedAt(LocalDateTime.now());
        employeeRepository.save(updatedEmployee);

        for (int i=0; i< newEmployee.getRoleIdList().size(); i++) {
            Long roleId = newEmployee.getRoleIdList().get(i);
            roleService.validateRoleId(roleId);
        }

        List<EmployeeRoleEntity> previousEmpRoles = employeeRoleRepository.findAllByEmployee(updatedEmployee);
        employeeRoleRepository.deleteAll(previousEmpRoles);

        createEmployeeRole(updatedEmployee, newEmployee.getRoleIdList());

        return convertToEmployeeRes(updatedEmployee);
    }

    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }

    public List<EmployeeRes> getEmployeeByTeam(Long teamId){
        teamService.validateTeamId(teamId);
        List<EmployeeEntity> entities = employeeRepository.findAllByTeamId(teamId);
        return convertToListEmployeeRes(entities);
    }

    public EmployeeRes getEmployeeResource(CustomUserDetails userDetails) {
        EmployeeEntity entity = userDetails.getUser();
        return this.convertToEmployeeRes(entity);
    }

    public EmployeeEntity validateEmployeeId(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new AppException(
                new ErrorResponse("not found employee with id[%s]", employeeId)));
    }

    public void createEmployeeRole (EmployeeEntity employee, List<Long> roleIds) {
        for (Long roleId : roleIds) {
            roleService.validateRoleId(roleId);
        }

        for (Long roleId : roleIds) {
            EmployeeRoleEntity entity = new EmployeeRoleEntity();
            entity.setEmployee(employee);
            entity.setRole(new RoleEntity(roleId));
            entity.setCreatedBy("Admin");
            entity.setUpdatedBy("Admin");
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            employeeRoleRepository.save(entity);
        }
    }
}
