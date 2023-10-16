package com.example.scoretracker.service.api;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.dto.role.RoleReq;
import com.example.scoretracker.model.dto.role.RoleRes;
import com.example.scoretracker.model.entity.RoleEntity;
import com.example.scoretracker.repository.RoleRepository;
import com.example.scoretracker.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleRes createRole(RoleReq req){
        RoleEntity roleEntity = new RoleEntity();

        if (req.getRoleName().isEmpty()){
            throw new AppException(new ErrorResponse("Role's name cannot be empty"));
        }

        roleEntity.setRoleName(req.getRoleName());
        roleEntity.setCreatedAt(LocalDateTime.now());
        roleEntity.setUpdatedAt(LocalDateTime.now());
        roleRepository.save(roleEntity);

        return convertToRoleRes(roleEntity);
    }

    private RoleRes convertToRoleRes(RoleEntity entity){
        RoleRes dto = new RoleRes();

        dto.setId(entity.getId());
        dto.setRoleName(entity.getRoleName());
        dto.setCreatedAt(CommonUtils.convertToString(entity.getCreatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));
        dto.setUpdatedAt(CommonUtils.convertToString(entity.getUpdatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));

        return dto;
    }
    public RoleRes findRoleById(Long id){
        RoleEntity roleEntity = roleRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found role with id[%s]", id)));
        return convertToRoleRes(roleEntity);
    }

    public List<RoleRes> findRoleList(){
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        return roleEntityList.stream().map(this::convertToRoleRes).toList();
    }

    public RoleRes updateRoleById(Long id, RoleReq newRole){
        RoleEntity updatedRole = roleRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found role with id[%s]", id)));

        updatedRole.setRoleName(newRole.getRoleName());
        updatedRole.setUpdatedAt(LocalDateTime.now());
        roleRepository.save(updatedRole);

        return convertToRoleRes(updatedRole);
    }

    public void deleteRoleById(Long id){
        roleRepository.deleteById(id);
    }

    public RoleEntity validateRoleId(Long roleId) {
        return roleRepository.findById(roleId).
                orElseThrow(() -> new AppException(new ErrorResponse("not found role with id[%s]", roleId)));
    }
}
