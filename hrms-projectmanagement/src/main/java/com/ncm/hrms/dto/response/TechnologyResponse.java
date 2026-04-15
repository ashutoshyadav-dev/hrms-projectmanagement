package com.ncm.hrms.dto.response;

import com.ncm.hrms.enums.TechnologyType;

public class TechnologyResponse {

	private Long id;
	private String name;
	private TechnologyType technologyType;

	public TechnologyResponse() {
	}

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

	public TechnologyType getTechnologyType() {
		return technologyType;
	}

	public void setTechnologyType(TechnologyType technologyType) {
		this.technologyType = technologyType;
	}
}
