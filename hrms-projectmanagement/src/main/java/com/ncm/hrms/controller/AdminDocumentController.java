package com.ncm.hrms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ncm.hrms.dto.response.AdminDocumentResponse;
import com.ncm.hrms.enums.UploadType;
import com.ncm.hrms.service.AdminDocumentService;

@RestController
@RequestMapping("api/adminDocument")
public class AdminDocumentController {

	private final AdminDocumentService adminDocSer;

	public AdminDocumentController(AdminDocumentService adminDocSer) {
		this.adminDocSer = adminDocSer;
	}

	@PostMapping("/upload")
	public ResponseEntity<AdminDocumentResponse> uploadBulkData(@RequestParam MultipartFile file,
			@RequestParam UploadType type) {

		return ResponseEntity.ok(adminDocSer.uploadBulkData(file, type));
	}

}
