package com.example.scoretracker.controller;

import com.example.scoretracker.model.common.ResponseDto;
import com.example.scoretracker.model.dto.fault.FaultReq;
import com.example.scoretracker.model.dto.fault.FaultRes;
import com.example.scoretracker.service.api.FaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fault")
public class FaultController {

    @Autowired
    private FaultService faultService;

    @PostMapping("/new/createFault")
    public ResponseDto<FaultRes> createFault(@RequestBody FaultReq req) {
        return new ResponseDto<>(faultService.createFault(req));
    }

    @GetMapping("/getFault/{id}")
    public ResponseDto<FaultRes> findFault(@PathVariable(name = "id") Long faultId) {
        return new ResponseDto<>(faultService.findFaultById(faultId));
    }

    @GetMapping("/getAllFault")
    public ResponseDto<List<FaultRes>> getAllFault() {
        return new ResponseDto<>(faultService.findAllFault());
    }

    @PutMapping("/edit/updateFault/{id}")
    public ResponseDto<FaultRes> updateFault(@PathVariable(name = "id") Long faultId,
                                             @RequestBody FaultReq req) {
        return new ResponseDto<>(faultService.updateFaultById(faultId, req));
    }

    @DeleteMapping("/delete/deleteFault/{id}")
    public void deleteFault(@PathVariable(name = "id") Long faultId) {
        faultService.deleteFaultById(faultId);
    }

}
