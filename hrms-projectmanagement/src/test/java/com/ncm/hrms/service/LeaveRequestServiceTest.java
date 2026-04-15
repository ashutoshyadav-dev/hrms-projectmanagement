package com.ncm.hrms.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ncm.hrms.dto.request.LeaveRequestDto;
import com.ncm.hrms.enums.LeaveType;

@SpringBootTest
public class LeaveRequestServiceTest {

	@Autowired
	private LeaveRequestService leaveReqSer;

	@Test
	void testLeaveApply() {

		String employeeEmail = "ramji1234@gmail.com";

		LeaveRequestDto dto = new LeaveRequestDto(LeaveType.SICK, LocalDate.now(), LocalDate.now().plusDays(2),
				"Testing leave application");

		leaveReqSer.applyLeave(employeeEmail, dto);

		System.out.println("Leave Apply Test Executed Successfully");
	}
}