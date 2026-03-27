package com.ncm.hrms.service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncm.hrms.dto.request.LeaveRequestDto;
import com.ncm.hrms.dto.response.LeaveResponseDto;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.LeaveRequest;
import com.ncm.hrms.enums.LeaveStatus;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.LeaveRequestRepository;

@Service
@Transactional
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveRequestService(
            LeaveRequestRepository leaveRequestRepository,
            EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

//    public LeaveResponseDto applyLeave(String employeeEmail, LeaveRequestDto dto) {
//
//        Employee employee = employeeRepository.findByEmail(employeeEmail)
//                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
//        
//           System.out.println("Employee Details: " + employee);
//        if (dto.getEndDate().isBefore(dto.getStartDate())) {
//            throw new IllegalArgumentException("End date cannot be before start date");
//        }
//
//        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
//
//        LeaveRequest leaveRequest = new LeaveRequest();
//        leaveRequest.setEmployee(employee);
//        leaveRequest.setLeaveType(dto.getLeaveType());
//        leaveRequest.setStartDate(dto.getStartDate());
//        leaveRequest.setEndDate(dto.getEndDate());
//        leaveRequest.setDaysRequested((int) days);
//        leaveRequest.setReason(dto.getReason());
//        leaveRequest.setLeaveStatus(LeaveStatus.PENDING);
//        leaveRequest.setAppliedDate(LocalDate.now());
//
//        return mapToResponseDto(leaveRequestRepository.save(leaveRequest));
//    }
    
    
    public LeaveResponseDto applyLeave(String employeeEmail, LeaveRequestDto dto) {

        Employee employee = employeeRepository.findByEmail(employeeEmail)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        
           
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
          
        LeaveRequest leaveReq =
                leaveRequestRepository.findByEmployee_IdAndAppliedDate(
                        employee.getId(),
                        LocalDate.now()
                );

        if (leaveReq != null && leaveReq.getLeaveStatus() != LeaveStatus.REJECTED) {
            throw new IllegalArgumentException( "You cannot apply more than one leave in a day");
        }

        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setLeaveType(dto.getLeaveType());
        leaveRequest.setStartDate(dto.getStartDate());
        leaveRequest.setEndDate(dto.getEndDate());
        leaveRequest.setDaysRequested((int) days);
        leaveRequest.setReason(dto.getReason());
        leaveRequest.setLeaveStatus(LeaveStatus.PENDING);
        leaveRequest.setAppliedDate(LocalDate.now());

        return mapToResponseDto(leaveRequestRepository.save(leaveRequest));
    }
    
    
    public LeaveResponseDto approveLeave(Long leaveId) {
    	
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));
        

    	if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING) {
    	    throw new IllegalStateException("Leave already processed");
    	}


        leaveRequest.setLeaveStatus(LeaveStatus.APPROVED);
        
        return mapToResponseDto(leaveRequestRepository.save(leaveRequest));
    }

   
    public LeaveResponseDto rejectLeave(Long leaveId) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

    	if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING) {
    	    throw new IllegalStateException("Leave already processed");
    	}


        leaveRequest.setLeaveStatus(LeaveStatus.REJECTED);
        return mapToResponseDto(leaveRequestRepository.save(leaveRequest));
    }
    
    public List<LeaveResponseDto> getAllLeaves(){
    	List<LeaveRequest> leaves = leaveRequestRepository.findAll();

        return leaves.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    	
    }

    
    private LeaveResponseDto mapToResponseDto(LeaveRequest leaveRequest) {

        LeaveResponseDto dto = new LeaveResponseDto();
        dto.setId(leaveRequest.getId());
        dto.setLeaveType(leaveRequest.getLeaveType());
        dto.setStartDate(leaveRequest.getStartDate());
        dto.setEndDate(leaveRequest.getEndDate());
        dto.setDaysRequested(leaveRequest.getDaysRequested());
        dto.setReason(leaveRequest.getReason());
        dto.setLeaveStatus(leaveRequest.getLeaveStatus());
        dto.setAppliedDate(leaveRequest.getAppliedDate());
        dto.setEmployeeId(leaveRequest.getEmployee().getId());
        dto.setEmployeeName(leaveRequest.getEmployee().getName());
        return dto;
    }
}
