package com.ncm.hrms.dto.request;

import com.ncm.hrms.enums.TechnologyType;

public class TechnologyRequest {

	private String name;
	private TechnologyType technologyType;

	public TechnologyRequest() {
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
