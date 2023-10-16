package com.example.scoretracker.service.api;

import com.example.scoretracker.common.Constant;
import com.example.scoretracker.exception.AppException;
import com.example.scoretracker.model.common.ErrorResponse;
import com.example.scoretracker.model.dto.employee.EmployeeRes;
import com.example.scoretracker.model.dto.fault.FaultRes;
import com.example.scoretracker.model.dto.team.TeamRes;
import com.example.scoretracker.model.dto.tracking.BonusRecipientReq;
import com.example.scoretracker.model.dto.tracking.EmployeeScoreRes;
import com.example.scoretracker.model.dto.tracking.TrackingReq;
import com.example.scoretracker.model.dto.tracking.TrackingRes;
import com.example.scoretracker.model.entity.EmployeeEntity;
import com.example.scoretracker.model.entity.TrackingEntity;
import com.example.scoretracker.repository.EmployeeRepository;
import com.example.scoretracker.repository.FaultRepository;
import com.example.scoretracker.repository.TrackingRepository;
import com.example.scoretracker.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TrackingService {

    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private FaultRepository faultRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FaultService faultService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TeamService teamService;

    public TrackingRes createTracking(TrackingReq req) {
        TrackingEntity entity = new TrackingEntity();

        //validate employeeId and faultId
        employeeService.validateEmployeeId(req.getEmployeeId());
        faultService.validateFaultId(req.getFaultId());

        if (StringUtils.isEmpty(req.getNote())) {
            throw new AppException(new ErrorResponse("Note cannot be empty"));
        }
        entity.setEmployeeId(req.getEmployeeId());
        entity.setFaultId(req.getFaultId());
        entity.setNote(req.getNote());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        trackingRepository.save(entity);

        return convertToTrackingRes(entity);
    }

    private TrackingRes convertToTrackingRes(TrackingEntity entity) {
        TrackingRes dto = new TrackingRes();

        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setFaultId(entity.getFaultId());
        dto.setNote(entity.getNote());
        dto.setCreatedAt(CommonUtils.convertToString(entity.getCreatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));
        dto.setUpdatedAt(CommonUtils.convertToString(entity.getUpdatedAt(), Constant.DATE_FORMAT.yyyy_MM_dd_HH_mm_ss));

        EmployeeRes employeeRes = employeeService.findEmployeeById(entity.getEmployeeId());
        dto.setEmployeeName(employeeRes.getEmployeeName());

        TeamRes teamRes = teamService.findTeamById(employeeRes.getTeamId());
        dto.setTeamName(teamRes.getTeamName());

        FaultRes faultRes = faultService.findFaultById(entity.getFaultId());
        dto.setFaultName(faultRes.getFaultName());

        return dto;
    }

    private List<TrackingRes> convertToTrackingResList(List<TrackingEntity> entity) {
        return entity.stream().map(this::convertToTrackingRes).toList();
    }

    public TrackingRes findTrackingById(Long id) {
        TrackingEntity entity = trackingRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found tracking with id[%s]", id)));
        return convertToTrackingRes(entity);
    }

    public List<TrackingRes> findAllTracking() {
        List<TrackingEntity> trackingEntities = trackingRepository.findAll();
        return convertToTrackingResList(trackingEntities);
    }

    public TrackingRes updateTrackingById(Long id, TrackingReq newTracking) {
        TrackingEntity updatedTracking = trackingRepository.findById(id).
                orElseThrow(() -> new AppException(new ErrorResponse("not found tracking with id[%s]", id)));

        updatedTracking.setEmployeeId(newTracking.getEmployeeId());
        updatedTracking.setFaultId(newTracking.getFaultId());
        updatedTracking.setNote(newTracking.getNote());
        updatedTracking.setUpdatedAt(LocalDateTime.now());
        trackingRepository.save(updatedTracking);

        return convertToTrackingRes(updatedTracking);
    }

    public void deleteTrackingById(Long id) {
        trackingRepository.deleteById(id);
    }

    //Supporting methods
    public List<TrackingRes> getMonthlyTrackingByEmployeeId(Long employeeId, int month, int year) {
        employeeService.validateEmployeeId(employeeId);
        List<TrackingEntity> entities = trackingRepository.findAllByEmployeeIdAndCreatedAtMonthYear(employeeId, month, year);
        return convertToTrackingResList(entities);
    }

    public double getMonthlyDeductedScore(Long employeeId, int month, int year) {
        employeeService.validateEmployeeId(employeeId);

        List<TrackingRes> resList = getMonthlyTrackingByEmployeeId(employeeId, month, year);
        double totalFaultScore = 0.0;

        for (TrackingRes res : resList) {
            double faultDefaultScore = faultRepository.findDefaultScoreByFaultId(res.getFaultId());
            totalFaultScore += faultDefaultScore;
        }

        return totalFaultScore;
    }

    private double getMonthlyTotalScoreByEmployeeId(Long employeeId, int month, int year) {
        double totalScore = 100.0;
        return totalScore + getMonthlyDeductedScore(employeeId, month, year);
    }

    public List<Double> getAllMonthlyScores(int month, int year) {
        List<Long> employeeIds = employeeRepository.findAllIds();
        List<Double> employeeScores = new ArrayList<>();
        for (Long id : employeeIds) {
            employeeScores.add(getMonthlyTotalScoreByEmployeeId(id, month, year));
        }
        return employeeScores;
    }

    //Tracking APIs
    public List<Double> getMonthlyScores(int month, int year) {
        return this.getAllMonthlyScores(month, year);
    }

    public List<TrackingRes> getMonthlyTrackingByEmployee(Long employeeId, Integer month, Integer year) {
        return this.getMonthlyTrackingByEmployeeId(employeeId, month, year);
    }

    public Double getMonthlyDeductedScores(Long employeeId, Integer month, Integer year) {
        return this.getMonthlyDeductedScore(employeeId, month, year);
    }

    public Double getMonthlyTotalScoreByEmployee(Long employeeId, Integer month, Integer year) {
        return this.getMonthlyTotalScoreByEmployeeId(employeeId, month, year);
    }

    public List<TrackingRes> getMonthlyTrackingByMonth(Integer month, Integer year) {
        return convertToTrackingResList(trackingRepository.getTrackingEntitiesByMonth(month, year));
    }

    public List<EmployeeRes> getEmployeesByFaultIdMonthly(Long faultId, Integer month, Integer year) {
        faultService.validateFaultId(faultId);

        List<Long> employeeIds = trackingRepository.getDistinctEmployeeIdByFaultId(faultId, month, year);
        List<EmployeeEntity> employees = new ArrayList<>();
        for (Long id : employeeIds) {
            EmployeeEntity entity = employeeRepository.findEmployeeEntitiesById(id);
            employees.add(entity);
        }
        return employeeService.convertToListEmployeeRes(employees);
    }

    public Map<Double, List<EmployeeRes>> getEmployeesWithMonthlyMaxScore(Integer month, Integer year) {
        List<Double> monthlyScores = this.getAllMonthlyScores(month, year);
        Double maxScore = Collections.max(monthlyScores);
        Double secondMaxScore = Collections.max(monthlyScores.stream().filter(score -> score < maxScore).toList());

        Map<Double, List<EmployeeRes>> finalList = new HashMap<>();

        List<EmployeeEntity> maxScoreEmployees = new ArrayList<>();
        List<EmployeeEntity> secondMaxScoreEmployees = new ArrayList<>();

        List<Long> employeeIds = employeeRepository.findAllIds();
        for (Long id : employeeIds) {
            double employeeScore = getMonthlyTotalScoreByEmployeeId(id, month, year);
            if (employeeScore == maxScore) {
                maxScoreEmployees.add(employeeRepository.findEmployeeEntitiesById(id));
            } else if (employeeScore == secondMaxScore) {
                secondMaxScoreEmployees.add(employeeRepository.findEmployeeEntitiesById(id));
            }
        }

        finalList.put(maxScore, employeeService.convertToListEmployeeRes(maxScoreEmployees));
        if (maxScoreEmployees.size() < 2) {
            finalList.put(secondMaxScore, employeeService.convertToListEmployeeRes(secondMaxScoreEmployees));
        }

        return finalList;
    }

    public EmployeeScoreRes getYearlyScoresByEmployee(Long employeeId, Integer year) {
        EmployeeEntity entity = employeeRepository.findEmployeeEntitiesById(employeeId);
        EmployeeRes employeeRes = employeeService.convertToEmployeeRes(entity);
        EmployeeScoreRes dto = new EmployeeScoreRes();

        Map<Integer, Double> scoreList = new HashMap<>();
        for (int month = 1; month <= 12; month++) {
            double monthlyScore = getMonthlyTotalScoreByEmployeeId(employeeId, month, year);
            scoreList.put(month, monthlyScore);
        }

        dto.setEmployeeRes(employeeRes);
        dto.setEmployeeScoreList(scoreList);
        return dto;
    }

    public Map<Double, EmployeeRes> applyMonthlyBonusScore(BonusRecipientReq req) {
        Long employeeId1 = req.getEmployeeId1();
        Long employeeId2 = req.getEmployeeId2();

        employeeService.validateEmployeeId(employeeId1);
        employeeService.validateEmployeeId(employeeId1);

        if (req.getScore1() + req.getScore2() > 4) {
            throw new AppException(new ErrorResponse("Bonus points cannot be more than 4"));
        }
        Double finalScoreEmp1 = getMonthlyTotalScoreByEmployee(employeeId1, req.getMonth(), req.getYear()) + req.getScore1();
        Double finalScoreEmp2 = getMonthlyTotalScoreByEmployee(employeeId2, req.getMonth(), req.getYear()) + req.getScore2();

        Map<Double, EmployeeRes> bonusRecipients = new HashMap<>();
        bonusRecipients.put(finalScoreEmp1, employeeService.findEmployeeById(employeeId1));
        bonusRecipients.put(finalScoreEmp2, employeeService.findEmployeeById(employeeId2));

        return bonusRecipients;
    }
}
