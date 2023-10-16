package com.example.scoretracker.controller;

import com.example.scoretracker.model.common.ResponseDto;
import com.example.scoretracker.model.dto.role.RoleReq;
import com.example.scoretracker.model.dto.role.RoleRes;
import com.example.scoretracker.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/new/createRole")
    public ResponseDto<RoleRes> createRole(@RequestBody RoleReq roleReq){
        return new ResponseDto<>(roleService.createRole(roleReq));
    }

    @GetMapping("/findRole/{id}")
    public ResponseDto<RoleRes> findRole(@PathVariable(name = "id") Long roleId){
        return new ResponseDto<>(roleService.findRoleById(roleId));
    }

    @GetMapping("/getAllRole/list")
    public ResponseDto<List<RoleRes>> findAllRole(){
        return new ResponseDto<>(roleService.findRoleList());
    }

    @PutMapping("/edit/updateRole/{id}")
    public ResponseDto<RoleRes> updateRole(@PathVariable(name = "id") Long roleId,
                              @RequestBody RoleReq roleReq){
        return new ResponseDto<>(roleService.updateRoleById(roleId, roleReq));
    }

    @DeleteMapping("/delete/deleteRole/{id}")
    public void deleteRole(@PathVariable(name = "id") Long roleId) {
        roleService.deleteRoleById(roleId);
    }
}
