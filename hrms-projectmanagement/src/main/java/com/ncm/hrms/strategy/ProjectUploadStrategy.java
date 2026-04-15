package com.ncm.hrms.strategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ncm.hrms.dto.response.AdminDocumentResponse;
import com.ncm.hrms.entity.Project;
import com.ncm.hrms.enums.ProjectStatus;
import com.ncm.hrms.repository.ProjectRepository;

@Component("PROJECT")
public class ProjectUploadStrategy implements FileUploadStrategy {

	private final ProjectRepository projectRepo;

	public ProjectUploadStrategy(ProjectRepository projectRepo) {
		this.projectRepo = projectRepo;
	}

	@Override
	public AdminDocumentResponse process(MultipartFile file) {

		List<String> errors = new ArrayList<>();
		List<Project> validList = new ArrayList<>();

		int total = 0;

		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {

				if (row.getRowNum() == 0)
					continue;

				total++;

				try {

					String name = getString(row.getCell(0));
					String desc = getString(row.getCell(1));
					String startStr = getString(row.getCell(2));
					String endStr = getString(row.getCell(3));
					String statusStr = getString(row.getCell(4));

					if (name == null || name.isEmpty()) {
						throw new RuntimeException("Project name is required");
					}

					if (startStr == null || endStr == null) {
						throw new RuntimeException("Start/End date required");
					}

					if (projectRepo.existsByProjectName(name)) {
						throw new RuntimeException("Project already exists");
					}

					LocalDate startDate = LocalDate.parse(startStr);
					LocalDate endDate = LocalDate.parse(endStr);

					if (endDate.isBefore(startDate)) {
						throw new RuntimeException("End date cannot be before start date");
					}

					ProjectStatus status = ProjectStatus.ACTIVE;

					if (statusStr != null && !statusStr.isEmpty()) {
						status = ProjectStatus.valueOf(statusStr.toUpperCase());
					}

					Project project = new Project();
					project.setProjectName(name);
					project.setDescription(desc);
					project.setStartDate(startDate);
					project.setEndDate(endDate);
					project.setStatus(status);

					validList.add(project);

				} catch (Exception e) {
					errors.add("Row " + (row.getRowNum() + 1) + " - " + e.getMessage());
				}
			}

			projectRepo.saveAll(validList);

		} catch (Exception e) {
			throw new RuntimeException("File processing failed", e);
		}

		return new AdminDocumentResponse(total, validList.size(), total - validList.size(), errors);
	}

	private String getString(Cell cell) {
		if (cell == null)
			return null;

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();

		case NUMERIC:
			return String.valueOf((long) cell.getNumericCellValue());

		default:
			return cell.toString().trim();
		}
	}
}