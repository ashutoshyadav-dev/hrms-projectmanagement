package com.ncm.hrms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.repository.EmployeeRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String email) {
		Employee emp = employeeRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Employee not found"));

		return User.builder().username(emp.getEmail()).password(emp.getPassword()).authorities(emp.getRole().name())
				.build();
	}
}
