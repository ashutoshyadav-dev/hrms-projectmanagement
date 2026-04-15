package com.ncm.hrms.strategy;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ncm.hrms.enums.UploadType;

@Component
public class FileUploadStrategyFactory {

	private final Map<String, FileUploadStrategy> strategyMap;

	public FileUploadStrategyFactory(Map<String, FileUploadStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	public FileUploadStrategy getStrategy(UploadType type) {

		FileUploadStrategy strategy = strategyMap.get(type.name());

		if (strategy == null) {
			throw new RuntimeException("Invalid type: " + type);
		}

		return strategy;
	}
}
