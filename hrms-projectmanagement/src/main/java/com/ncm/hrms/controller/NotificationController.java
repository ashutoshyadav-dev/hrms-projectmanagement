package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.Notification;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.NotificationRepository;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationRepository repo;

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping
	public List<Notification> getMyNotifications(Authentication auth) {

		String email = auth.getName();

		Employee emp = employeeRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return repo.findByUserIdOrderByCreatedAtDesc(emp.getId());
	}

	@PutMapping("/mark-read/{id}")
	public void markAsRead(@PathVariable Long id) {
		Notification n = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

		n.setRead(true);
		repo.save(n);
	}

	@GetMapping("/unread-count/{userId}")
	public long getUnreadCount(@PathVariable Long userId) {
		return repo.countByUserIdAndIsReadFalse(userId);
	}
}
