package com.ncm.hrms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.common.AllowedIpDto;
import com.ncm.hrms.entity.AllowedIp;
import com.ncm.hrms.exception.IpNotAllowedException;
import com.ncm.hrms.repository.AllowedIpRepository;

@Service
public class AllowedIpService {

	private final AllowedIpRepository allowedIpRepo;

	public AllowedIpService(AllowedIpRepository allowedIpRepo) {
		this.allowedIpRepo = allowedIpRepo;
	}

	public AllowedIpDto addAllowedIp(AllowedIpDto dto) {

		AllowedIp savedIp = allowedIpRepo.save(dtoTOEntity(dto));

		return entityTODto(savedIp);
	}

	public void removeAllowedIp(Long id) {

		AllowedIp ip = allowedIpRepo.findById(id).orElseThrow(() -> new RuntimeException("IP not found"));

		allowedIpRepo.delete(ip);
	}

	public List<AllowedIpDto> getAllAllowedIp() {

		List<AllowedIp> ips = allowedIpRepo.findAll();

		return ips.stream().map(this::entityTODto).collect(Collectors.toList());
	}

	public AllowedIpDto updateAllowedIp(Long id, AllowedIpDto dto) {

		AllowedIp existingIp = allowedIpRepo.findById(id).orElseThrow(() -> new RuntimeException("IP not found"));

		existingIp.setIpAddress(dto.getIpAddress());
		existingIp.setDescription(dto.getDescription());
		existingIp.setActive(dto.getActive());

		AllowedIp updatedIp = allowedIpRepo.save(existingIp);

		return entityTODto(updatedIp);
	}

	public AllowedIp validateIp(String ip) {

		return allowedIpRepo.findByIpAddressAndActiveTrue(ip)
				.orElseThrow(() -> new IpNotAllowedException("Access denied. IP not allowed: " + ip));
	}

	public AllowedIpDto entityTODto(AllowedIp ip) {

		AllowedIpDto dto = new AllowedIpDto();

		dto.setIpAddress(ip.getIpAddress());
		dto.setDescription(ip.getDescription());
		dto.setActive(ip.getActive());
		dto.setId(ip.getId());
		return dto;
	}

	public AllowedIp dtoTOEntity(AllowedIpDto dto) {

		AllowedIp allowedIp = new AllowedIp();

		allowedIp.setIpAddress(dto.getIpAddress());
		allowedIp.setDescription(dto.getDescription());
		allowedIp.setActive(dto.getActive());

		return allowedIp;
	}
}