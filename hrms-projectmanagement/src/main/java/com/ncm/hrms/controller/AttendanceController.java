package com.ncm.hrms.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncm.hrms.dto.request.AttendanceRequest;
import com.ncm.hrms.dto.response.AttendanceResponse;
import com.ncm.hrms.service.AttendanceService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/log")
    public AttendanceResponse logAttendance(@RequestBody AttendanceRequest request,HttpServletRequest httpRequest) {
        return attendanceService.logAttendance(request, httpRequest);
    }
    
    @GetMapping("/{empId}")
    public List<AttendanceResponse> getAttendance(
            @PathVariable Long empId,
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return attendanceService.getAttendanceByEmployeeId(empId, startDate, endDate);
    }
}
