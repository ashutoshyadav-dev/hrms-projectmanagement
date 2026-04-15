package com.ncm.hrms.dto.response;

public class DesignationResponse {

	private Long id;
	private String title;

	private String description;
	private Double baseSalary;

	public DesignationResponse() {
		super();
	}

	public DesignationResponse(Long id, String title, String description, Double baseSalary) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.baseSalary = baseSalary;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}

}
