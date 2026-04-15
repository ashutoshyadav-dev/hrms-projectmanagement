package com.ncm.hrms.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ncm.hrms.enums.LeaveStatus;
import com.ncm.hrms.enums.LeaveType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class LeaveRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	@JsonIgnoreProperties({ "leaveRequests", "assignments", "technologies", "password" })
	private Employee employee;

	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	private Integer daysRequested;

	private String reason;

	@Enumerated(EnumType.STRING)
	private LeaveStatus leaveStatus;

	@CreationTimestamp
	private LocalDate appliedDate;

	public LeaveRequest() {
		super();
	}

	public LeaveRequest(Employee employee, LeaveType leaveType, LocalDate startDate, LocalDate endDate,
			Integer daysRequested, String reason, LeaveStatus leaveStatus, LocalDate appliedDate) {
		super();
		this.employee = employee;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.daysRequested = daysRequested;
		this.reason = reason;
		this.leaveStatus = leaveStatus;
		this.appliedDate = appliedDate;
	}

	public LeaveRequest(Employee employee, LeaveType leaveType, LocalDate startDate, LocalDate endDate,
			Integer daysRequested, String reason, LeaveStatus leaveStatus) {
		super();
		this.employee = employee;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.daysRequested = daysRequested;
		this.reason = reason;
		this.leaveStatus = leaveStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
