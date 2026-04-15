package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncm.hrms.dto.common.AllowedIpDto;
import com.ncm.hrms.service.AllowedIpService;

@RestController
@RequestMapping("/api/ip")
public class AllowedIpController {

	private final AllowedIpService allowedIpService;

	public AllowedIpController(AllowedIpService allowedIpService) {
		this.allowedIpService = allowedIpService;
	}

	@PostMapping
	public AllowedIpDto addIp(@RequestBody AllowedIpDto dto) {
		return allowedIpService.addAllowedIp(dto);
	}

	@GetMapping
	public List<AllowedIpDto> getAllIps() {
		return allowedIpService.getAllAllowedIp();
	}

	@DeleteMapping("/{id}")
	public void deleteIp(@PathVariable Long id) {
		allowedIpService.removeAllowedIp(id);
	}

	@PutMapping("/{id}")
	public AllowedIpDto updateIp(@PathVariable Long id, @RequestBody AllowedIpDto dto) {
		return allowedIpService.updateAllowedIp(id, dto);
	}
}