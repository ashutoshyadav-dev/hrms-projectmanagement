package com.ncm.hrms.entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ncm.hrms.enums.AttendanceStatus;

@Entity
public class Attendance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	private LocalDateTime checkInTime;

	private LocalDateTime checkOutTime;

	private Duration workingHours;

	private Boolean late;

	private Boolean earlyExit;

	@Enumerated(EnumType.STRING)
	private AttendanceStatus status;

	private String checkInIp;

	private String checkOutIp;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public Attendance() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public Duration getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(Duration workingHours) {
		this.workingHours = workingHours;
	}

	public Boolean getLate() {
		return late;
	}

	public void setLate(Boolean late) {
		this.late = late;
	}

	public Boolean getEarlyExit() {
		return earlyExit;
	}

	public void setEarlyExit(Boolean earlyExit) {
		this.earlyExit = earlyExit;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

	public String getCheckInIp() {
		return checkInIp;
	}

	public void setCheckInIp(String checkInIp) {
		this.checkInIp = checkInIp;
	}

	public String getCheckOutIp() {
		return checkOutIp;
	}

	public void setCheckOutIp(String checkOutIp) {
		this.checkOutIp = checkOutIp;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
