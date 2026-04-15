package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncm.hrms.dto.request.TechnologyRequest;
import com.ncm.hrms.dto.response.TechnologyResponse;
import com.ncm.hrms.service.TechnologyService;

@RestController
@RequestMapping("/technologies")
public class TechnologyController {

	private final TechnologyService technologyService;

	public TechnologyController(TechnologyService technologyService) {
		this.technologyService = technologyService;
	}

	@PostMapping
	public ResponseEntity<TechnologyResponse> createTechnology(@RequestBody TechnologyRequest request) {

		return ResponseEntity.ok(technologyService.createTechnology(request));
	}

	@GetMapping
	public ResponseEntity<List<TechnologyResponse>> getAllTechnologies() {
		return ResponseEntity.ok(technologyService.getAllTechnologies());
	}

	@GetMapping("/{id}")
	public ResponseEntity<TechnologyResponse> getTechnologyById(@PathVariable Long id) {

		return ResponseEntity.ok(technologyService.getTechnologyById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<TechnologyResponse> updateTechnology(@PathVariable Long id,
			@RequestBody TechnologyRequest request) {

		return ResponseEntity.ok(technologyService.updateTechnology(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTechnology(@PathVariable Long id) {

		technologyService.deleteTechnology(id);
		return ResponseEntity.ok("Technology deleted successfully");
	}
}
