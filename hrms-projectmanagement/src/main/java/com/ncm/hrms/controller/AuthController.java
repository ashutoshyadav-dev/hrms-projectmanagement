package com.ncm.hrms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.ncm.hrms.dto.request.AuthRequest;
import com.ncm.hrms.dto.request.RegisterRequest;
import com.ncm.hrms.service.AuthService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {

		String token = authService.login(request.getEmail(), request.getPassword());

		return ResponseEntity.ok().body(Map.of("token", token));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

		authService.register(request);

		return ResponseEntity.ok("Registered Successfully");
	}

}
