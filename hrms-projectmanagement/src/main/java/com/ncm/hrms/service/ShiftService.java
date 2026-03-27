package com.ncm.hrms.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.common.ShiftDto;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.Shift;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.ShiftRepository;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepo;
    private final EmployeeRepository employeeRepo;

    public ShiftService(ShiftRepository shiftRepo,
                        EmployeeRepository employeeRepo) {

        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepo;
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

        List<ShiftDto> listShiftDto = shiftRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();

        return listShiftDto;
    }

    public ShiftDto updateShift(Long shiftId, ShiftDto shiftDto) {

        Shift shift = shiftRepo.findById(shiftId)
                .orElseThrow(() ->
                        new RuntimeException("Shift not found"));

        shift.setName(shiftDto.getName());
        shift.setStartTime(shiftDto.getStartTime());
        shift.setEndTime(shiftDto.getEndTime());
        shift.setFlexibleStartLimit(shiftDto.getFlexibleStartLimit());
        shift.setRequiredWorkHours( shiftDto.getRequiredWorkHours());
        shift.setFlexible(shiftDto.getFlexible());

        Shift updatedShift = shiftRepo.save(shift);

        return mapToDto(updatedShift);
    }

   

    public void deleteShift(Long shiftId) {

        Shift shift = shiftRepo.findById(shiftId)
                .orElseThrow(() ->
                        new RuntimeException("Shift not found"));

        shiftRepo.delete(shift);
    }

    
    public ShiftDto getShiftById(Long shiftId) {

        Shift shift = shiftRepo.findById(shiftId)
                .orElseThrow(() ->
                        new RuntimeException("Shift not found"));

        return mapToDto(shift);
    }

   

    public void assignShiftToEmployee(Long shiftId, Long employeeId) {

        Shift shift = shiftRepo.findById(shiftId)
                .orElseThrow(() ->
                        new RuntimeException("Shift not found"));

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));

        employee.setShift(shift);

        employeeRepo.save(employee);
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