package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ncm.hrms.dto.common.ShiftDto;
import com.ncm.hrms.service.ShiftService;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

	private final ShiftService shiftService;

	public ShiftController(ShiftService shiftService) {
		this.shiftService = shiftService;
	}

	@PostMapping
	public ShiftDto createShift(@RequestBody ShiftDto dto) {
		return shiftService.createShift(dto);
	}

	@GetMapping
	public List<ShiftDto> getAllShifts() {
		return shiftService.getAllShifts();
	}

	@PutMapping("/{id}")
	public ShiftDto updateShift(@PathVariable Long id, @RequestBody ShiftDto dto) {

		return shiftService.updateShift(id, dto);
	}

	@DeleteMapping("/{id}")
	public String deleteShift(@PathVariable Long id) {

		shiftService.deleteShift(id);

		return "Shift deleted successfully";
	}

	@GetMapping("/{id}")
	public ShiftDto getShift(@PathVariable Long id) {

		return shiftService.getShiftById(id);
	}

	@PostMapping("/assign")
	public String assignShift(@RequestParam Long shiftId, @RequestParam Long employeeId) {

		shiftService.assignShiftToEmployee(shiftId, employeeId);

		return "Shift assigned successfully";
	}

}