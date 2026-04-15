package com.ncm.hrms.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ncm.hrms.enums.EmpRole;
import com.ncm.hrms.dto.request.RegisterRequest;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private NotificationService notiSer;

	@Autowired
	private JwtUtil jwtUtil;

	public void register(RegisterRequest request) {

		if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists");
		}

		Employee emp = new Employee();
		emp.setName(request.getName());
		emp.setEmail(request.getEmail());
		emp.setPassword(passwordEncoder.encode(request.getPassword()));
		emp.setRole(EmpRole.ROLE_EMPLOYEE);

		employeeRepository.save(emp);
		try {
			Long adminId = 5L;
			notiSer.createNotification(adminId,
					"New Employee Register " + emp.getName() + " and Request for activating account!!");

		} catch (Exception e) {
			System.out.println("Notification failed: " + e.getMessage());
		}

	}

	public String login(String email, String password) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		Employee emp = employeeRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Employee does not found by id: " + email));

		LocalDateTime previousLastLogin = emp.getLastLogin();

		emp.setLastLogin(LocalDateTime.now());

		employeeRepository.save(emp);

		return jwtUtil.generateToken(email, emp.getRole().name(), emp.getName(), previousLastLogin, emp.getId());
	}

}
