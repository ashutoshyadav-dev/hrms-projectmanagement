package com.ncm.hrms.strategy;

import org.springframework.web.multipart.MultipartFile;

import com.ncm.hrms.dto.response.AdminDocumentResponse;

public interface FileUploadStrategy {
	AdminDocumentResponse process(MultipartFile file);
}
