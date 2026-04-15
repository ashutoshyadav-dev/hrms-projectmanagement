package com.ncm.hrms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.ncm.hrms.enums.LogType;

@Entity
public class AttendanceLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime timestamp;

	@Enumerated(EnumType.STRING)
	private LogType type;

	private String ipAddress;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public AttendanceLog() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public LogType getType() {
		return type;
	}

	public void setType(LogType type) {
		this.type = type;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}