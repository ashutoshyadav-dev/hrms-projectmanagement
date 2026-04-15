package com.ncm.hrms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.request.DesignationRequest;
import com.ncm.hrms.dto.response.DesignationResponse;
import com.ncm.hrms.entity.Designation;
import com.ncm.hrms.repository.DesignationRepository;

@Service
public class DesignationService {
	private final DesignationRepository designationRepository;

	public DesignationService(DesignationRepository designationRepository) {
		this.designationRepository = designationRepository;
	}

	private DesignationResponse toResponse(Designation designation) {
		return new DesignationResponse(designation.getId(), designation.getTitle(), designation.getDescription(),
				designation.getBaseSalary());
	}

	private Designation toEntity(DesignationRequest request) {
		Designation designation = new Designation();
		designation.setTitle(request.getTitle());
		designation.setDescription(request.getDescription());
		designation.setBaseSalary(request.getBaseSalary());
		return designation;
	}

	public DesignationResponse createDesignation(DesignationRequest request) {
		Designation saved = designationRepository.save(toEntity(request));
		return toResponse(saved);
	}

	public DesignationResponse getDesignationById(Long id) {
		Designation designation = designationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Designation not found with id: " + id));
		return toResponse(designation);
	}

	public List<DesignationResponse> getAllDesignations() {
		return designationRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
	}

	public DesignationResponse updateDesignation(Long id, DesignationRequest request) {
		Designation designation = designationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Designation not found with id: " + id));

		designation.setTitle(request.getTitle());
		designation.setDescription(request.getDescription());
		designation.setBaseSalary(request.getBaseSalary());

		return toResponse(designationRepository.save(designation));
	}

	public void deleteDesignation(Long id) {
		if (!designationRepository.existsById(id)) {
			throw new RuntimeException("Designation not found with id: " + id);
		}
		designationRepository.deleteById(id);
	}

}
