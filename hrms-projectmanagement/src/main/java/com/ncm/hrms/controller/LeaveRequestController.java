package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ncm.hrms.dto.request.LeaveRequestDto;
import com.ncm.hrms.dto.response.LeaveResponseDto;
import com.ncm.hrms.service.LeaveRequestService;

@RestController
@RequestMapping("/leaves")
@CrossOrigin
public class LeaveRequestController {

	private final LeaveRequestService leaveRequestService;

	public LeaveRequestController(LeaveRequestService leaveRequestService) {
		this.leaveRequestService = leaveRequestService;
	}

	@PostMapping("/apply")
	public ResponseEntity<LeaveResponseDto> applyLeave(Authentication authentication,
			@RequestBody LeaveRequestDto dto) {

		String email = authentication.getName();
		return ResponseEntity.status(HttpStatus.CREATED).body(leaveRequestService.applyLeave(email, dto));
	}

	@GetMapping
	public ResponseEntity<List<LeaveResponseDto>> getAllLeaves() {
		return ResponseEntity.ok(leaveRequestService.getAllLeaves());
	}

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@PutMapping("/{leaveId}/approve")
	public ResponseEntity<LeaveResponseDto> approveLeave(@PathVariable Long leaveId) {
		return ResponseEntity.ok(leaveRequestService.approveLeave(leaveId));
	}

	@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
	@PutMapping("/{leaveId}/reject")
	public ResponseEntity<LeaveResponseDto> rejectLeave(@PathVariable Long leaveId) {
		return ResponseEntity.ok(leaveRequestService.rejectLeave(leaveId));
	}
}
