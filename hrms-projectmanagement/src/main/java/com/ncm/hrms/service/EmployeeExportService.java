package com.ncm.hrms.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.response.EmployeeResponse;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class EmployeeExportService {

    
    public void exportToCSV(List<EmployeeResponse> list, PrintWriter writer) {

        writer.println("ID,Name,Email,Phone,Education,Date Of Birth,Designation,Hire Date,Status,Role");

        for (EmployeeResponse e : list) {
        	String designation = (e.getDesignation() != null) 
        	        ? e.getDesignation().getTitle() 
        	        : "";

        	String hireDate = (e.getHireDate() != null) 
        	        ? e.getHireDate().toString() 
        	        : "";

        	writer.println(
        	        e.getId() + "," +
        	        e.getName() + "," +
        	        e.getEmail() + "," +
        	        e.getPhoneNumber() + "," +
        	        e.getEducation() + "," +
        	        e.getDateOfBirth() + "," +
        	        designation + "," +
        	        hireDate + "," +
        	        e.getStatus() + "," +
        	        e.getRole()
        	);
        }
    }

   
    public void exportToExcel(List<EmployeeResponse> list, HttpServletResponse response) throws IOException {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");

        Row header = sheet.createRow(0);
        String[] columns = {"ID","Name","Email","Phone","Education","Status","Role","Designation","Date of Birth","Hire Date"};

        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        int rowIdx = 1;
        for (EmployeeResponse e : list) {

            Row row = sheet.createRow(rowIdx++);

            String designation = (e.getDesignation() != null)
                    ? e.getDesignation().getTitle()
                    : "";

            String dob = (e.getDateOfBirth() != null)
                    ? e.getDateOfBirth().toString()
                    : "";

            String hireDate = (e.getHireDate() != null)
                    ? e.getHireDate().toString()
                    : "";

            row.createCell(0).setCellValue(e.getId());
            row.createCell(1).setCellValue(e.getName());
            row.createCell(2).setCellValue(e.getEmail());
            row.createCell(3).setCellValue(e.getPhoneNumber());
            row.createCell(4).setCellValue(e.getEducation());
            row.createCell(5).setCellValue(String.valueOf(e.getStatus()));
            row.createCell(6).setCellValue(String.valueOf(e.getRole()));
            row.createCell(7).setCellValue(designation);
            row.createCell(8).setCellValue(dob);
            row.createCell(9).setCellValue(hireDate);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

   
    public void exportToPDF(List<EmployeeResponse> list, HttpServletResponse response) throws IOException {

        com.lowagie.text.Document document = new com.lowagie.text.Document();
        com.lowagie.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        document.add(new com.lowagie.text.Paragraph("Employee Report"));

        com.lowagie.text.pdf.PdfPTable table = new com.lowagie.text.pdf.PdfPTable(5);

        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Email");
        table.addCell("Status");
        table.addCell("Role");
        table.addCell("Education");
        table.addCell("Designation");
        table.addCell("Date of Birth");
        table.addCell("Hire Date");

        for (EmployeeResponse e : list) {

            String designation = (e.getDesignation() != null)
                    ? e.getDesignation().getTitle()
                    : "";

            String dob = (e.getDateOfBirth() != null)
                    ? e.getDateOfBirth().toString()
                    : "";

            String hireDate = (e.getHireDate() != null)
                    ? e.getHireDate().toString()
                    : "";

            table.addCell(String.valueOf(e.getId()));
            table.addCell(e.getName());
            table.addCell(e.getEmail());
            table.addCell(String.valueOf(e.getStatus()));
            table.addCell(String.valueOf(e.getRole()));
            table.addCell(e.getEducation());
            table.addCell(designation);
            table.addCell(dob);
            table.addCell(hireDate);
        }

        document.add(table);
        document.close();
    }
}
