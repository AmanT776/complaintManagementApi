package com.example.demo.service.compliant;

import com.example.demo.dto.compliant.CompliantResponse;
import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.UpdateRequest;
import com.example.demo.enums.Status;
import com.example.demo.exception.ResourceNotFound;
import com.example.demo.mapper.CompliantMapper;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.file.FileService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CompliantService implements ICompliantService {

    @Autowired
    private CompliantRepository compliantRepository;

    @Autowired
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    private final CompliantMapper compliantMapper =
            Mappers.getMapper(CompliantMapper.class);


       //CREATE COMPLAINT

    @Override
    public CompliantResponse createCompliant(CreateRequest createRequest) {

        Compliant compliant =
                compliantMapper.mapCompliantRequestToCompliant(createRequest);
        compliant.setFiles(new ArrayList<>());

        OrganizationalUnit organizationalUnit =
                organizationalUnitRepository.findById(
                                createRequest.getOrganizationalUnitId())
                        .orElseThrow(() ->
                                new ResourceNotFound("Organizational unit not found"));

        Category category =
                categoryRepository.findById(createRequest.getCategoryId())
                        .orElseThrow(() ->
                                new ResourceNotFound("Category not found"));

        compliant.setOrganizationalUnit(organizationalUnit);
        compliant.setCategory(category);

        Boolean isAnonymous = createRequest.getIsAnonymous();
        Long userId = createRequest.getUserId();

        if (Boolean.TRUE.equals(isAnonymous) && userId != null) {
            throw new ResourceNotFound("userId must be null when isAnonymous is true");
        }

        if (Boolean.FALSE.equals(isAnonymous)) {
            if (userId == null) {
                throw new ResourceNotFound("userId is required when isAnonymous is false");
            }
            User user = userRepository.findById(userId)
                    .orElseThrow(() ->
                            new ResourceNotFound("User not found"));
            compliant.setUser(user);
            compliant.setIsAnonymous(false);
        } else {
            compliant.setIsAnonymous(true);
            compliant.setUser(null);
        }

        if (Boolean.TRUE.equals(compliant.getIsAnonymous())) {
            compliant.setReferenceNumber(UUID.randomUUID().toString());
        }

        Compliant savedCompliant = compliantRepository.save(compliant);

        // Handle files
        List<MultipartFile> files = createRequest.getFiles();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String filePath;
                    try {
                        filePath = fileService.storeFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload file", e);
                    }

                    Files fileEntity = new Files();
                    fileEntity.setFile_path(filePath);
                    fileEntity.setCompliant(savedCompliant);
                    savedCompliant.getFiles().add(fileEntity);
                }
            }
            savedCompliant = compliantRepository.save(savedCompliant);
        }

        return compliantMapper.mapCompliantToCompliantResponse(savedCompliant);
    }


       //GET ALL COMPLAINTS

    @Override
    public List<CompliantResponse> getAllComplaints() {
        return compliantRepository.findAll()
                .stream()
                .map(compliantMapper::mapCompliantToCompliantResponse)
                .toList();
    }


      // GET BY ID

    @Override
    public CompliantResponse getCompliantById(long id) {
        Compliant compliant = compliantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound("Complaint not found with id: " + id));

        return compliantMapper.mapCompliantToCompliantResponse(compliant);
    }


     //  GET BY REFERENCE

    @Override
    public CompliantResponse getCompliantByReference(String referenceNumber) {
        Compliant compliant = compliantRepository.findByReferenceNumber(referenceNumber)
                .orElseThrow(() ->
                        new ResourceNotFound("Complaint not found with reference: " + referenceNumber));

        return compliantMapper.mapCompliantToCompliantResponse(compliant);
    }

       //UPDATE STATUS

    @Override
    public CompliantResponse updateStatus(long id, Status status) {
        Compliant compliant = compliantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound("Complaint not found with id: " + id));

        compliant.setStatus(status);
        Compliant updated = compliantRepository.save(compliant);

        return compliantMapper.mapCompliantToCompliantResponse(updated);
    }


       //UPDATE COMPLAINT

    @Override
    public CompliantResponse updateCompliant(long id, UpdateRequest updateRequest) {

        Compliant existingCompliant = compliantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Complaint not found with id: " + id));

        if (updateRequest.getTitle() != null && !updateRequest.getTitle().isEmpty()) {
            existingCompliant.setTitle(updateRequest.getTitle());
        }

        if (updateRequest.getDescription() != null &&
                !updateRequest.getDescription().isEmpty()) {
            existingCompliant.setDescription(updateRequest.getDescription());
        }

        if (updateRequest.getIsAnonymous() != null) {
            existingCompliant.setIsAnonymous(updateRequest.getIsAnonymous());
        }

        if (updateRequest.getOrganizationalUnitId() != null) {
            OrganizationalUnit organizationalUnit =
                    organizationalUnitRepository.findById(
                                    updateRequest.getOrganizationalUnitId())
                            .orElseThrow(() ->
                                    new ResourceNotFound("Organizational unit not found"));
            existingCompliant.setOrganizationalUnit(organizationalUnit);
        }

        if (updateRequest.getCategoryId() != null) {
            Category category =
                    categoryRepository.findById(updateRequest.getCategoryId())
                            .orElseThrow(() ->
                                    new ResourceNotFound("Category not found"));
            existingCompliant.setCategory(category);
        }

        if (updateRequest.getUserId() != null) {
            User user =
                    userRepository.findById(updateRequest.getUserId())
                            .orElseThrow(() ->
                                    new ResourceNotFound("User not found"));
            existingCompliant.setUser(user);
        }

        // Handle new files
        List<MultipartFile> files = updateRequest.getFiles();
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String filePath;
                    try {
                        filePath = fileService.storeFile(file);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to upload file", e);
                    }

                    Files fileEntity = new Files();
                    fileEntity.setFile_path(filePath);
                    fileEntity.setCompliant(existingCompliant);
                    existingCompliant.getFiles().add(fileEntity);
                }
            }
        }

        Compliant updated = compliantRepository.save(existingCompliant);
        return compliantMapper.mapCompliantToCompliantResponse(updated);
    }

       //DELETE COMPLAINT

    @Override
    public void deleteCompliant(long id) {
        Compliant compliant = compliantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFound("Complaint not found with id: " + id));
        compliantRepository.delete(compliant);
    }
}
