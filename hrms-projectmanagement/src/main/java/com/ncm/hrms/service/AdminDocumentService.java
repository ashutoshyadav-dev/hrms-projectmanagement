package com.ncm.hrms.service;

import org.springframework.web.multipart.MultipartFile;

import com.ncm.hrms.dto.response.AdminDocumentResponse;
import com.ncm.hrms.enums.UploadType;
import com.ncm.hrms.strategy.FileUploadStrategy;
import com.ncm.hrms.strategy.FileUploadStrategyFactory;

import org.springframework.stereotype.Service;

@Service
public class AdminDocumentService {

	private final FileUploadStrategyFactory factory;

	public AdminDocumentService(FileUploadStrategyFactory factory) {
		this.factory = factory;
	}

	public AdminDocumentResponse uploadBulkData(MultipartFile file, UploadType type) {

		FileUploadStrategy strategy = factory.getStrategy(type);

		return strategy.process(file);
	}

}
