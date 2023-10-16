package com.example.scoretracker.controller;

import com.example.scoretracker.model.common.ResponseDto;
import com.example.scoretracker.model.dto.employee.EmployeeRes;
import com.example.scoretracker.model.dto.tracking.BonusRecipientReq;
import com.example.scoretracker.model.dto.tracking.EmployeeScoreRes;
import com.example.scoretracker.model.dto.tracking.TrackingReq;
import com.example.scoretracker.model.dto.tracking.TrackingRes;
import com.example.scoretracker.service.api.TrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tracking")
public class TrackingController {

    @Autowired
    private TrackingService trackingService;

    @PostMapping("/createTracking")
    public ResponseDto<TrackingRes> createTracking(@RequestBody TrackingReq req) {
        return new ResponseDto<>(trackingService.createTracking(req));
    }

    @GetMapping("/getTracking/{id}")
    public ResponseDto<TrackingRes> getTracking(@PathVariable(name = "id") Long trackingId) {
        return new ResponseDto<>(trackingService.findTrackingById(trackingId));
    }

    @GetMapping("/getTracking/list")
    public ResponseDto<List<TrackingRes>> getAllTracking() {
        return new ResponseDto<>(trackingService.findAllTracking());
    }

    @PutMapping("/editTracking/{id}")
    public ResponseDto<TrackingRes> updateTracking(@PathVariable(name = "id") Long trackingId,
                                                   @RequestBody TrackingReq trackingReq) {
        return new ResponseDto<>(trackingService.updateTrackingById(trackingId, trackingReq));
    }

    @DeleteMapping("/deleteTracking/{id}")
    public void deleteTracking(@PathVariable(name = "id") Long trackingId) {
        trackingService.deleteTrackingById(trackingId);
    }

    //Monthly
    @GetMapping("/getAllMonthlyScores")
    public ResponseDto<List<Double>> getAllMonthlyScores(@RequestParam("month") int month,
                                                         @RequestParam("year") int year) {
        return new ResponseDto<>(trackingService.getMonthlyScores(month, year));
    }

    @GetMapping("/getMonthlyTrackingByEmployee/{employeeId}")
    public ResponseDto<List<TrackingRes>> getMonthlyTrackingByEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                                                       @RequestParam("month") int month,
                                                                       @RequestParam("year") int year) {
        return new ResponseDto<>(trackingService.getMonthlyTrackingByEmployee(employeeId, month, year));
    }

    @GetMapping("/getMonthlyDeductedScore/{employeeId}")
    public ResponseDto<Double> getMonthlyDeductedScoreByEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                                                 @RequestParam("month") int month,
                                                                 @RequestParam("year") int year) {
        return new ResponseDto<>(trackingService.getMonthlyDeductedScores(employeeId, month, year));
    }

    @GetMapping("/getMonthlyScoreByEmployee/{employeeId}")
    public ResponseDto<Double> getMonthlyScoreByEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                                           @RequestParam("month") int month,
                                                           @RequestParam("year") int year) {
        return new ResponseDto<>(trackingService.getMonthlyTotalScoreByEmployee(employeeId, month, year));
    }

    @GetMapping("/getTrackingByMonth")
    public ResponseDto<List<TrackingRes>> getTrackingByMonth(@RequestParam("month") int month,
                                                             @RequestParam("year") int year) {
        return new ResponseDto<>(trackingService.getMonthlyTrackingByMonth(month, year));
    }

    @GetMapping("/getEmployeeByFaultMonthly/{faultId}")
    public ResponseDto<List<EmployeeRes>> getEmployeeByFaultMonthly(@PathVariable(name = "faultId") Long faultId,
                                                                    @RequestParam("month") int month,
                                                                    @RequestParam("year") int year) {
        return new ResponseDto<>(trackingService.getEmployeesByFaultIdMonthly(faultId, month, year));
    }

    @GetMapping("/getMonthlyMaxScoreEmployees")
    public ResponseDto<Map<Double, List<EmployeeRes>>> getMonthlyMaxScoreEmployees(@RequestParam("month") int month,
                                                                                   @RequestParam("year") int year) {
        return new ResponseDto<>(trackingService.getEmployeesWithMonthlyMaxScore(month, year));
    }

    @GetMapping("/getYearlyScoresByEmployee")
    public ResponseDto<EmployeeScoreRes> getYearlyScoresByEmployee(@RequestParam("employeeId") Long employeeId,
                                                                   @RequestParam("year") Integer year) {
        return new ResponseDto<>(trackingService.getYearlyScoresByEmployee(employeeId, year));
    }

    @GetMapping("/edit/applyBonusPoints")
    public ResponseDto<Map<Double, EmployeeRes>> applyBonusPoints(@RequestBody BonusRecipientReq bonusRecipientReq) {
        return new ResponseDto<>(trackingService.applyMonthlyBonusScore(bonusRecipientReq));
    }

}
