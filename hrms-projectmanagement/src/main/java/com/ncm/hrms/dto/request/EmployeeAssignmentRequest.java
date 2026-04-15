package com.ncm.hrms.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ncm.hrms.enums.ProjectStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class EmployeeAssignmentRequest {

	@NotNull
	private Long employeeId;

	@NotNull
	private Long projectId;

	@NotNull
	private Long moduleId;

	@NotNull
	private LocalDate assignedDate;

	@PositiveOrZero
	private BigDecimal hoursWorked;

	private ProjectStatus projectStatus;

	public EmployeeAssignmentRequest() {
	}

	public EmployeeAssignmentRequest(Long employeeId, Long projectId, Long moduleId, LocalDate assignedDate,
			BigDecimal hoursWorked, ProjectStatus projectStatus) {
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.moduleId = moduleId;
		this.assignedDate = assignedDate;
		this.hoursWorked = hoursWorked;
		this.projectStatus = projectStatus;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public BigDecimal getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(BigDecimal hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}
}
