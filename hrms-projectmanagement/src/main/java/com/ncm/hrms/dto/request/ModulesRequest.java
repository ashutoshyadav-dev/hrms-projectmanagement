package com.ncm.hrms.dto.request;

public class ModulesRequest {

	private String name;
	private String description;
	private Long projectId;
	private Long employeeId;

	public ModulesRequest() {
	}

	public ModulesRequest(String name, String description, Long projectId, Long employeeId) {
		super();
		this.name = name;
		this.description = description;
		this.projectId = projectId;
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
}
