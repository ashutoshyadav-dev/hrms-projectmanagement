package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncm.hrms.dto.request.DesignationRequest;
import com.ncm.hrms.dto.response.DesignationResponse;
import com.ncm.hrms.service.DesignationService;

@RestController
@RequestMapping("/designations")
public class DesignationController {

	private final DesignationService designationService;

	public DesignationController(DesignationService designationService) {
		this.designationService = designationService;
	}

	@PostMapping
	public ResponseEntity<DesignationResponse> create(@RequestBody DesignationRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(designationService.createDesignation(request));
	}

	@GetMapping
	public ResponseEntity<List<DesignationResponse>> getAll() {
		return ResponseEntity.ok(designationService.getAllDesignations());
	}

	@GetMapping("/{id}")
	public ResponseEntity<DesignationResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(designationService.getDesignationById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<DesignationResponse> update(@PathVariable Long id, @RequestBody DesignationRequest request) {
		return ResponseEntity.ok(designationService.updateDesignation(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		designationService.deleteDesignation(id);
		return ResponseEntity.ok("Designation deleted successfully");
	}

}
