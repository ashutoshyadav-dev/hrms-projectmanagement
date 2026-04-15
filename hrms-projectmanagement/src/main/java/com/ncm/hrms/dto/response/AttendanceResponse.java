package com.ncm.hrms.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ncm.hrms.dto.common.ShiftDto;
import com.ncm.hrms.enums.AttendanceStatus;

public class AttendanceResponse {

	private Long attendanceId;

	private String employeeName;

	private LocalDate date;

	private ShiftDto shift;

	private LocalDateTime checkIn;

	private LocalDateTime checkOut;

	private AttendanceStatus status;

	private String workingHours;

	private Long employeeId;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDateTime getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDateTime checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDateTime getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDateTime checkOut) {
		this.checkOut = checkOut;
	}

	public AttendanceStatus getStatus() {
		return status;
	}

	public void setStatus(AttendanceStatus status) {
		this.status = status;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public ShiftDto getShift() {
		return shift;
	}

	public void setShift(ShiftDto shift) {
		this.shift = shift;
	}

}