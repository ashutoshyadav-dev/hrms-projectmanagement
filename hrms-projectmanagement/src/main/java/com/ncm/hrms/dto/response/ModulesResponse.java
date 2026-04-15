package com.ncm.hrms.dto.response;

public class ModulesResponse {

	private Long id;
	private String name;
	private String description; // ✅ Add this
	private Long projectId; // ✅ Add this
	private Long employeeId; // ✅ Add this

	// Default constructor
	public ModulesResponse() {
	}

	// All-args constructor
	public ModulesResponse(Long id, String name, String description, Long projectId, Long employeeId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.projectId = projectId;
		this.employeeId = employeeId;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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