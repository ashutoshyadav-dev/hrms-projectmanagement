package com.ncm.hrms.entity;

import jakarta.persistence.*;

@Entity
public class AllowedIp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ipAddress;

	private String description;

	private Boolean active;

	public AllowedIp() {
		super();
	}

	public AllowedIp(Long id, String ipAddress, String description, Boolean active) {
		super();
		this.id = id;
		this.ipAddress = ipAddress;
		this.description = description;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
