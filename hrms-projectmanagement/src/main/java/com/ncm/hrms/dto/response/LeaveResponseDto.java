package com.ncm.hrms.dto.response;

import java.time.LocalDate;

import com.ncm.hrms.enums.LeaveStatus;
import com.ncm.hrms.enums.LeaveType;

public class LeaveResponseDto {

	private Long id;

	private Long employeeId;
	private String employeeName;

	private LeaveType leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer daysRequested;
	private LeaveStatus leaveStatus;
	private LocalDate appliedDate;
	private String reason;

	public LeaveResponseDto() {
	}

	public LeaveResponseDto(Long id, Long employeeId, String employeeName, LeaveType leaveType, LocalDate startDate,
			LocalDate endDate, Integer daysRequested, LeaveStatus leaveStatus, LocalDate appliedDate, String reason) {

		this.id = id;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.daysRequested = daysRequested;
		this.leaveStatus = leaveStatus;
		this.appliedDate = appliedDate;
		this.reason = reason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getDaysRequested() {
		return daysRequested;
	}

	public void setDaysRequested(Integer daysRequested) {
		this.daysRequested = daysRequested;
	}

	public LeaveStatus getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(LeaveStatus leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

	public LocalDate getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(LocalDate appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
