package com.ncm.hrms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ncm.hrms.dto.common.ShiftDto;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.Shift;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.ShiftRepository;

@Service
public class ShiftService {

	private static final Logger log = LoggerFactory.getLogger(ShiftService.class);

	private final ShiftRepository shiftRepo;
	private final EmployeeRepository employeeRepo;
	private final NotificationService notiSer;

	public ShiftService(ShiftRepository shiftRepo, EmployeeRepository employeeRepo, NotificationService notiSer) {

		this.shiftRepo = shiftRepo;
		this.employeeRepo = employeeRepo;
		this.notiSer = notiSer;
	}

	public ShiftDto createShift(ShiftDto shiftDto) {

		Shift shift = new Shift();

		shift.setName(shiftDto.getName());
		shift.setStartTime(shiftDto.getStartTime());
		shift.setEndTime(shiftDto.getEndTime());
		shift.setFlexibleStartLimit(shiftDto.getFlexibleStartLimit());
		shift.setRequiredWorkHours(shiftDto.getRequiredWorkHours());
		shift.setFlexible(shiftDto.getFlexible());

		Shift savedShift = shiftRepo.save(shift);

		return mapToDto(savedShift);
	}

	public List<ShiftDto> getAllShifts() {

		List<ShiftDto> listShiftDto = shiftRepo.findAll().stream().map(this::mapToDto).toList();

		return listShiftDto;
	}

	public ShiftDto updateShift(Long shiftId, ShiftDto shiftDto) {

		Shift shift = shiftRepo.findById(shiftId).orElseThrow(() -> new RuntimeException("Shift not found"));

		shift.setName(shiftDto.getName());
		shift.setStartTime(shiftDto.getStartTime());
		shift.setEndTime(shiftDto.getEndTime());
		shift.setFlexibleStartLimit(shiftDto.getFlexibleStartLimit());
		shift.setRequiredWorkHours(shiftDto.getRequiredWorkHours());
		shift.setFlexible(shiftDto.getFlexible());

		Shift updatedShift = shiftRepo.save(shift);

		return mapToDto(updatedShift);
	}

	public void deleteShift(Long shiftId) {

		Shift shift = shiftRepo.findById(shiftId).orElseThrow(() -> new RuntimeException("Shift not found"));
		if (shift.getEmployees() != null && !shift.getEmployees().isEmpty()) {
			throw new RuntimeException("Cannot delete shift. Employees are assigned.");
		}

		shiftRepo.delete(shift);
	}

	public ShiftDto getShiftById(Long shiftId) {

		Shift shift = shiftRepo.findById(shiftId).orElseThrow(() -> new RuntimeException("Shift not found"));

		return mapToDto(shift);
	}

	@Transactional
	public void assignShiftToEmployee(Long shiftId, Long employeeId) {

		Shift shift = shiftRepo.findById(shiftId).orElseThrow(() -> new RuntimeException("Shift not found"));

		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		employee.setShift(shift);

		employeeRepo.save(employee);

		try {
			notiSer.createNotification(employeeId, "Shift '" + shift.getName() + "' assigned to you");
		} catch (Exception e) {
			log.error("Notification failed for employeeId {}: {}", employeeId, e.getMessage());
		}
	}

	private ShiftDto mapToDto(Shift shift) {

		ShiftDto dto = new ShiftDto();

		dto.setId(shift.getId());
		dto.setName(shift.getName());
		dto.setStartTime(shift.getStartTime());
		dto.setEndTime(shift.getEndTime());
		dto.setFlexibleStartLimit(shift.getFlexibleStartLimit());
		dto.setRequiredWorkHours(shift.getRequiredWorkHours());
		dto.setFlexible(shift.getFlexible());

		return dto;
	}
}