package com.ncm.hrms.dto.request;

import java.time.LocalDate;

public class ProjectRequest {
	private String projectName;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;

	public ProjectRequest() {
		super();
	}

	public ProjectRequest(String projectName, String description, LocalDate startDate, LocalDate endDate) {
		super();
		this.projectName = projectName;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
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

}
