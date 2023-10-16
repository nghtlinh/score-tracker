package com.example.scoretracker.service.api;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.dto.fault.FaultReq;
import com.example.scoretracker.model.dto.fault.FaultRes;
import com.example.scoretracker.model.entity.FaultEntity;
import com.example.scoretracker.repository.FaultRepository;
import com.example.scoretracker.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FaultService {

    @Autowired
    private FaultRepository faultRepository;

    public FaultRes createFault(FaultReq req){
        FaultEntity faultEntity = new FaultEntity();

        if (StringUtils.isEmpty(req.getFaultName())) {
            throw new AppException(new ErrorResponse("name cannot be empty"));
        }

        if (req.getDefaultScore() >= 0) {
            throw new AppException(new ErrorResponse("default score must be negative"));
        }

        faultEntity.setFaultName(req.getFaultName());
        faultEntity.setDefaultScore(req.getDefaultScore());
        faultEntity.setCreatedAt(LocalDateTime.now());
        faultEntity.setUpdatedAt(LocalDateTime.now());
        faultRepository.save(faultEntity);

        return convertToFaultRes(faultEntity);
    }

    private FaultRes convertToFaultRes(FaultEntity entity) {
        FaultRes dto = new FaultRes();

        dto.setId(entity.getId());
        dto.setFaultName(entity.getFaultName());
        dto.setDefaultScore(entity.getDefaultScore());
        dto.setCreatedAt(CommonUtils.convertToString(entity.getCreatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));
        dto.setUpdatedAt(CommonUtils.convertToString(entity.getUpdatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));

        return dto;
    }

    public FaultRes findFaultById(Long id) {
        FaultEntity faultEntity = faultRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found fault with id[%s]", id)));
        return convertToFaultRes(faultEntity);
    }

    public List<FaultRes> findAllFault() {
        List<FaultEntity> faultEntities = faultRepository.findAll();
        return faultEntities.stream().map(this::convertToFaultRes).toList();
    }

    public FaultRes updateFaultById(Long id, FaultReq newFault) {
        FaultEntity updatedFault = faultRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found fault with id[%s]", id)));

        updatedFault.setFaultName(newFault.getFaultName());
        updatedFault.setDefaultScore(newFault.getDefaultScore());
        updatedFault.setUpdatedAt(LocalDateTime.now());
        faultRepository.save(updatedFault);

        return convertToFaultRes(updatedFault);
    }

    public void deleteFaultById(Long id) {
        faultRepository.deleteById(id);
    }

    public void validateFaultId(Long faultId) {
        if (faultRepository.findById(faultId).isEmpty()) {
            throw new AppException(new ErrorResponse("not found fault with id[%s]", faultId));
        }
    }
}
