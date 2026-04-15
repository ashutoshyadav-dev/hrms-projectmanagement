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
import com.ncm.hrms.enums.EmpStatus;
import com.ncm.hrms.enums.LeaveStatus;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.LeaveRequestRepository;

@Service
@Transactional
public class LeaveRequestService {

	private final LeaveRequestRepository leaveRequestRepository;
	private final EmployeeRepository employeeRepository;
	private final NotificationService notificationService;

	public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository,
			NotificationService notificationService) {
		this.leaveRequestRepository = leaveRequestRepository;
		this.employeeRepository = employeeRepository;
		this.notificationService = notificationService;
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

		if (employee.getStatus() != EmpStatus.ACTIVE) {
			throw new IllegalArgumentException("Employee is not active ");
		}
		if (dto.getEndDate().isBefore(dto.getStartDate())) {
			throw new IllegalArgumentException("Leave end date cannot before start date");
		}

		if (dto.getLeaveType() == null) {
			throw new IllegalArgumentException("Leave type cannot be empty");
		}

		long hDays = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
		if (hDays > 3) {
			throw new IllegalArgumentException("You cannot take more than 3 days leave");
		}

		LocalDate today = LocalDate.now();
		LocalDate minDate = today.minusMonths(1);
		LocalDate maxDate = today.plusMonths(1);

		LocalDate startDate = dto.getStartDate();

		if (startDate.isBefore(minDate) || startDate.isAfter(maxDate)) {
			throw new IllegalArgumentException(
					"You can only apply leave between 1 month before and 1 month after today");
		}

		LeaveRequest leaveReq = leaveRequestRepository.findByEmployee_IdAndAppliedDate(employee.getId(),
				LocalDate.now());

		if (leaveReq != null && leaveReq.getLeaveStatus() != LeaveStatus.REJECTED) {
			throw new IllegalArgumentException("You cannot apply more than one leave in a day");
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

		LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

		try {
			Long adminId = 5L;
			notificationService.createNotification(adminId, "New Leave Request from " + employee.getName());

		} catch (Exception e) {
			System.out.println("Notification failed: " + e.getMessage());
		}

		return mapToResponseDto(saved);
	}

	public LeaveResponseDto approveLeave(Long leaveId) {

		LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
				.orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

		if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING) {
			throw new IllegalStateException("Leave already processed");
		}

		leaveRequest.setLeaveStatus(LeaveStatus.APPROVED);

		LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

		try {

			notificationService.createNotification(leaveRequest.getEmployee().getId(), "Your leave from "
					+ leaveRequest.getStartDate() + " to " + leaveRequest.getEndDate() + " is approved");

		} catch (Exception e) {
			System.out.println("Notification failed: " + e.getMessage());
		}

		return mapToResponseDto(saved);
	}

	public LeaveResponseDto rejectLeave(Long leaveId) {

		LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
				.orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

		if (leaveRequest.getLeaveStatus() != LeaveStatus.PENDING) {
			throw new IllegalStateException("Leave already processed");
		}

		leaveRequest.setLeaveStatus(LeaveStatus.REJECTED);

		LeaveRequest saved = leaveRequestRepository.save(leaveRequest);

		try {

			notificationService.createNotification(leaveRequest.getEmployee().getId(), "Your leave from "
					+ leaveRequest.getStartDate() + " to " + leaveRequest.getEndDate() + " is rejected");

		} catch (Exception e) {
			System.out.println("Notification failed: " + e.getMessage());
		}

		return mapToResponseDto(saved);

	}

	public List<LeaveResponseDto> getAllLeaves() {
		List<LeaveRequest> leaves = leaveRequestRepository.findAll();

		return leaves.stream().map(this::mapToResponseDto).collect(Collectors.toList());

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
