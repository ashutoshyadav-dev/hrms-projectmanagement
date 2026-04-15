package com.ncm.hrms.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ncm.hrms.enums.ProjectStatus;

public class ProjectResponse {
	private Long projectId;
	private String projectName;
	private String description;
	private ProjectStatus status;

	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime createdAt;

	public ProjectResponse(Long projectId, String projectName, String description, ProjectStatus status,
			LocalDate startDate, LocalDate endDate, LocalDateTime createdAt) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.description = description;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createdAt = createdAt;
	}

	public ProjectResponse() {
		super();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
