package com.ncm.hrms.dto.response;

import java.time.LocalDate;

import com.ncm.hrms.enums.LeaveStatus;
import com.ncm.hrms.enums.LeaveType;

public class LeaveAdminResposeDto {
	private Long leaveId;

	private Long employeeId;
	private String employeeName;

	private LeaveType leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer daysRequested;
	private LeaveStatus leaveStatus;
	private LocalDate appliedDate;

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
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

}
