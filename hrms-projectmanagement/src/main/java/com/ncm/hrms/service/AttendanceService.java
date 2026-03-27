package com.ncm.hrms.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.common.ShiftDto;
import com.ncm.hrms.dto.request.AttendanceRequest;
import com.ncm.hrms.dto.response.AttendanceResponse;
import com.ncm.hrms.entity.Attendance;
import com.ncm.hrms.entity.AttendanceLog;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.Shift;
import com.ncm.hrms.enums.AttendanceStatus;
import com.ncm.hrms.enums.EmpStatus;
import com.ncm.hrms.enums.LogType;
import com.ncm.hrms.repository.AttendanceLogRepository;
import com.ncm.hrms.repository.AttendanceRepository;
import com.ncm.hrms.repository.EmployeeRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final AttendanceLogRepository attendanceLogRepo;
    private final EmployeeRepository empRepo;
    private final AllowedIpService allowedIpService;

    public AttendanceService(
            AttendanceRepository attendanceRepo,
            AttendanceLogRepository attendanceLogRepo,
            EmployeeRepository empRepo,
            AllowedIpService allowedIpService) {

        this.attendanceRepo = attendanceRepo;
        this.attendanceLogRepo = attendanceLogRepo;
        this.empRepo = empRepo;
        this.allowedIpService = allowedIpService;
    }


    

    public AttendanceResponse logAttendance( AttendanceRequest requestDto,HttpServletRequest request) {
    	
        Employee employee = getEmployee(requestDto.getEmployeeId());

        validateEmployeeStatus(employee);

        String ip = getClientIp(request);

        validateIp(ip);

        AttendanceLog log = createAttendanceLog(
                employee,
                ip,
                requestDto.getType()   
        );

        attendanceLogRepo.save(log);

        List<AttendanceLog> logs = getTodayLogs(employee.getId());

        Attendance attendance = processAttendance(employee, logs);

        return buildResponse(employee, attendance);
    }



    private Employee getEmployee(Long employeeId) {

        return empRepo.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));
    }


    private void validateEmployeeStatus(Employee employee) {

        if (employee.getStatus() != EmpStatus.ACTIVE) {
            throw new RuntimeException("Employee is not active");
        }
    }


    private void validateIp(String ip) {

        allowedIpService.validateIp(ip);
    }



    private String getClientIp(HttpServletRequest request) {

        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }



    private AttendanceLog createAttendanceLog(
            Employee employee,
            String ip,
            LogType type) {

        AttendanceLog log = new AttendanceLog();

        log.setEmployee(employee);
        log.setTimestamp(LocalDateTime.now());
        log.setType(type);
        log.setIpAddress(ip);

        return log;
    }



//    private List<AttendanceLog> getTodayLogs(Long employeeId) {
//
//        return attendanceLogRepo.findTodayLogsByEmployee(employeeId);
//    }

    
    private List<AttendanceLog> getTodayLogs(Long employeeId) {

        LocalDate today = LocalDate.now();

        LocalDateTime startOfDay = today.atStartOfDay();

        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        return attendanceLogRepo.findTodayLogsByEmployee(
                employeeId,
                startOfDay,
                endOfDay
        );
    }


    private Attendance processAttendance(
            Employee employee,
            List<AttendanceLog> logs) {

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepo
                .findByEmployeeIdAndDate(employee.getId(), today)
                .orElse(new Attendance());

        attendance.setEmployee(employee);
        attendance.setDate(today);

        
        AttendanceLog firstCheckInLog = logs.stream()
                .filter(l -> l.getType() == LogType.CHECK_IN)
                .min((a,b) -> a.getTimestamp().compareTo(b.getTimestamp()))
                .orElse(null);

        AttendanceLog lastCheckOutLog = logs.stream()
                .filter(l -> l.getType() == LogType.CHECK_OUT)
                .max((a,b) -> a.getTimestamp().compareTo(b.getTimestamp()))
                .orElse(null);
        
        LocalDateTime firstCheckIn=null;
        LocalDateTime lastCheckOut=null;
        
        if(firstCheckInLog != null){
        	firstCheckIn=firstCheckInLog.getTimestamp();
            attendance.setCheckInTime(firstCheckInLog.getTimestamp());
            attendance.setCheckInIp(firstCheckInLog.getIpAddress());
        }

        if(lastCheckOutLog != null){
        	lastCheckOut=lastCheckOutLog.getTimestamp();
            attendance.setCheckOutTime(lastCheckOutLog.getTimestamp());
            attendance.setCheckOutIp(lastCheckOutLog.getIpAddress());
        }
        
        Shift shift=employee.getShift();
        
        
        if (firstCheckIn == null) {
            attendance.setStatus(AttendanceStatus.ABSENT);
            return attendanceRepo.save(attendance);
        } 
       
        LocalTime checkInTime = attendance.getCheckInTime().toLocalTime();

//        LocalTime startReference = Boolean.TRUE.equals(shift.getFlexible())
//                ? shift.getFlexibleStartLimit()
//                : shift.getStartTime();
//
//        if (checkInTime.isAfter(startReference) && 
//                (Boolean.TRUE.equals(shift.getFlexible())
//                    ? Duration.between(shift.getFlexibleStartLimit(), checkInTime)
//                    : Duration.between(shift.getStartTime(), checkInTime)
//                ).compareTo(Duration.ofHours(2)) > 0) {
//
//            attendance.setStatus(AttendanceStatus.HALF_DAY);
//            attendance.setLate(true);
//
//        }
//        else if (checkInTime.isAfter(
//                Boolean.TRUE.equals(shift.getFlexible())
//                        ? shift.getFlexibleStartLimit()
//                        : shift.getStartTime()
//        )) {
//
//            attendance.setStatus(AttendanceStatus.LATE);
//            attendance.setLate(true);
//
//        }
//        else {
//
//            attendance.setStatus(AttendanceStatus.PRESENT);
//            attendance.setLate(false);
//        }
//        
        LocalTime ref = Boolean.TRUE.equals(shift.getFlexible())
        	    ? shift.getFlexibleStartLimit()
        	    : shift.getStartTime();

        	Duration lateDuration = checkInTime.isAfter(ref)
        	    ? Duration.between(ref, checkInTime)
        	    : Duration.ZERO;

        	if (lateDuration.compareTo(Duration.ofHours(2)) > 0) {
        	    attendance.setStatus(AttendanceStatus.HALF_DAY);
        	    attendance.setLate(true);
        	} else if (lateDuration.compareTo(Duration.ZERO) > 0) {
        	    attendance.setStatus(AttendanceStatus.LATE);
        	    attendance.setLate(true);
        	} else {
        	    attendance.setStatus(AttendanceStatus.PRESENT);
        	    attendance.setLate(false);
        	}
        if (firstCheckIn != null && lastCheckOut != null) {

            Duration workingHours = Duration.between(firstCheckIn, lastCheckOut);
            attendance.setWorkingHours(workingHours);
 
            if (shift.getRequiredWorkHours() != null && workingHours != null) {

                long workedHours = workingHours.toHours();

                if (workedHours < shift.getRequiredWorkHours()) {
                    attendance.setEarlyExit(true);
                } else {
                    attendance.setEarlyExit(false);
                }
            }
            
        }
        
        
        
        return attendanceRepo.save(attendance);
    }


    
    //=========================================================================================================================
    
    public List<AttendanceResponse> getAttendanceByEmployeeId( Long empId, LocalDate start, LocalDate end) {
        Employee emp = empRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        
        LocalDate hireDate = emp.getHireDate();
        if (start.isBefore(hireDate)) {
            start = hireDate;
        }
       
        
        List<Attendance> attendanceList =
                attendanceRepo.findAllAttendanceByEmployeeIdAndRange(empId, start, end);

        
        Map<LocalDate, Attendance> attendanceMap = new HashMap<>();
        for (Attendance a : attendanceList) {
            attendanceMap.put(a.getDate(), a);
        }

        List<AttendanceResponse> resList = new ArrayList<>();
        LocalDate today = LocalDate.now();

       
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {

            if (attendanceMap.containsKey(date)) {
               
                resList.add(buildResponse(emp, attendanceMap.get(date)));

            } else {
                
                AttendanceResponse res = new AttendanceResponse();

                res.setEmployeeId(emp.getId());
                res.setEmployeeName(emp.getName());
                res.setDate(date);
                res.setShift(mapToShiftDto(emp.getShift()));

                if (date.isAfter(today)) {
                    res.setStatus(AttendanceStatus.UPCOMING);  

                } else if (date.isEqual(today)) {
                    res.setStatus(AttendanceStatus.PENDING);   

                } else {
                    res.setStatus(AttendanceStatus.ABSENT);    
                }

                resList.add(res);
            }
        }

        return resList;
    }
    
    //=======================================================================================================================================
    
                           
    

    private AttendanceResponse buildResponse( Employee employee,Attendance attendance) {

        AttendanceResponse response = new AttendanceResponse();
        response.setEmployeeId(employee.getId());
        response.setEmployeeName(employee.getName());
        response.setDate(attendance.getDate());
        response.setCheckIn(attendance.getCheckInTime());
        response.setCheckOut(attendance.getCheckOutTime());
        response.setStatus(attendance.getStatus());
        response.setShift(mapToShiftDto(employee.getShift()));

        return response;
    }
    
    private ShiftDto mapToShiftDto(Shift shift) {

        if (shift == null) return null;

        ShiftDto dto = new ShiftDto();
        dto.setId(shift.getId());
        dto.setName(shift.getName());
        dto.setStartTime(shift.getStartTime());
        dto.setEndTime(shift.getEndTime());
        dto.setFlexibleStartLimit(shift.getFlexibleStartLimit());
        dto.setRequiredWorkHours(
            shift.getRequiredWorkHours() != null
                ? shift.getRequiredWorkHours()
                : null
        );
        dto.setFlexible(shift.getFlexible());

        return dto;
    }
}