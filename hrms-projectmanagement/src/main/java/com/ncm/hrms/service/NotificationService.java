package com.ncm.hrms.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncm.hrms.entity.Notification;
import com.ncm.hrms.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository repo;

	public void createNotification(Long userId, String message) {
		Notification n = new Notification();
		n.setUserId(userId);
		n.setMessage(message);
		n.setRead(false);
		n.setCreatedAt(LocalDateTime.now());

		repo.save(n);
	}
}
