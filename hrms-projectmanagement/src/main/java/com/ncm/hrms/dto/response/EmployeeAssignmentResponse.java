package com.ncm.hrms.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ncm.hrms.enums.ProjectStatus;

public class EmployeeAssignmentResponse {

	private Long assignmentId;

	private Long employeeId;
	private String employeeName;

	private Long projectId;
	private String projectName;

	private Long moduleId;
	private String moduleName;

	private LocalDate assignedDate;
	private BigDecimal hoursWorked;
	private ProjectStatus projectStatus;

	private LocalDate projectStartDate;
	private LocalDate projectEndDate;

	private String projectDescription;
	private String moduledescription;

	public EmployeeAssignmentResponse() {
	}

	public EmployeeAssignmentResponse(Long assignmentId, Long employeeId, String employeeName, Long projectId,
			String projectName, Long moduleId, String moduleName, LocalDate assignedDate, BigDecimal hoursWorked,
			ProjectStatus projectStatus) {
		this.assignmentId = assignmentId;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.projectId = projectId;
		this.projectName = projectName;
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.assignedDate = assignedDate;
		this.hoursWorked = hoursWorked;
		this.projectStatus = projectStatus;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
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

	public LocalDate getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(LocalDate projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public LocalDate getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(LocalDate projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getModuledescription() {
		return moduledescription;
	}

	public void setModuledescription(String moduledescription) {
		this.moduledescription = moduledescription;
	}

}
