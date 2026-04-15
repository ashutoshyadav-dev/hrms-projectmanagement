package com.ncm.hrms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.request.TechnologyRequest;
import com.ncm.hrms.dto.response.TechnologyResponse;
import com.ncm.hrms.entity.Technology;
import com.ncm.hrms.repository.TechnologyRepository;

@Service
public class TechnologyService {

	private final TechnologyRepository technologyRepository;

	public TechnologyService(TechnologyRepository technologyRepository) {
		this.technologyRepository = technologyRepository;
	}

	public TechnologyResponse createTechnology(TechnologyRequest request) {

		if (technologyRepository.existsByNameIgnoreCase(request.getName())) {
			throw new RuntimeException("Technology already exists");
		}

		Technology technology = new Technology();
		technology.setName(request.getName());
		technology.setTechnologyType(request.getTechnologyType());

		Technology saved = technologyRepository.save(technology);

		return mapToResponse(saved);
	}

	public List<TechnologyResponse> getAllTechnologies() {
		return technologyRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
	}

	public TechnologyResponse getTechnologyById(Long id) {
		Technology technology = technologyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Technology not found"));

		return mapToResponse(technology);
	}

	public TechnologyResponse updateTechnology(Long id, TechnologyRequest request) {

		Technology technology = technologyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Technology not found"));

		technology.setName(request.getName());
		technology.setTechnologyType(request.getTechnologyType());

		Technology updated = technologyRepository.save(technology);

		return mapToResponse(updated);
	}

	public void deleteTechnology(Long id) {

		Technology technology = technologyRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Technology not found"));

		technologyRepository.delete(technology);
	}

	private TechnologyResponse mapToResponse(Technology technology) {
		TechnologyResponse response = new TechnologyResponse();
		response.setId(technology.getId());
		response.setName(technology.getName());
		response.setTechnologyType(technology.getTechnologyType());
		return response;
	}
}
