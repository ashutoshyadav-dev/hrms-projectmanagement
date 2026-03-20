package com.ncm.hrms.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ncm.hrms.dto.common.DocumentDto;
import com.ncm.hrms.entity.Document;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.enums.ContentType;
import com.ncm.hrms.repository.DocumentRepository;
import com.ncm.hrms.repository.EmployeeRepository;

@Service
public class DocumentService {

    private DocumentRepository docRepo;
    private EmployeeRepository empRepo;

    public DocumentService(DocumentRepository docRepo, EmployeeRepository empRepo) {
        this.docRepo = docRepo;
        this.empRepo = empRepo;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    

    public DocumentDto uploadFile(DocumentDto docDto, MultipartFile file) throws IOException {

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String dbPath = uploadDir + "/" + fileName;

        Employee emp = empRepo.findById(docDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + docDto.getEmployeeId()));

        Document doc = dtoToDoc(docDto);

        doc.setEmployee(emp);
        doc.setPath(dbPath);
        doc.setUploadTime(new Timestamp(System.currentTimeMillis()));

        docRepo.save(doc);

        return docToDTO(doc);
    }

   

    public DocumentDto getDocumentById(Long id) {

        Document doc = docRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id " + id));

        return docToDTO(doc);
    }

    

    public List<DocumentDto> getAllDocuments() {

        return docRepo.findAll()
                .stream()
                .map(this::docToDTO)
                .collect(Collectors.toList());
    }

   

    public void deleteDocumentById(Long id) throws IOException {

        Document doc = docRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id " + id));

        Path path = Paths.get(doc.getPath());

        Files.deleteIfExists(path);

        docRepo.deleteById(id);
    }

   
    public Document dtoToDoc(DocumentDto docDto) {

        Document doc = new Document();
        doc.setDocName(docDto.getDocName());
        doc.setDocType(docDto.getDocType());
        doc.setContentType(ContentType.valueOf(docDto.getContentType()));

        return doc;
    }


    public DocumentDto docToDTO(Document doc) {

        DocumentDto dto = new DocumentDto();

        dto.setId(doc.getId());
        dto.setDocName(doc.getDocName());
        dto.setDocType(doc.getDocType());
        dto.setContentType(doc.getContentType().toString());
        dto.setPath(doc.getPath());

        if (doc.getEmployee() != null) {
            dto.setEmployeeId(doc.getEmployee().getId());
        }

        return dto;
    }
}