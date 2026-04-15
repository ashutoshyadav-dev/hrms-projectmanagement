package com.ncm.hrms.dto.request;

import com.ncm.hrms.enums.LogType;

public class AttendanceRequest {

	private Long employeeId;

	private LogType type;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LogType getType() {
		return type;
	}

	public void setType(LogType type) {
		this.type = type;
	}
}