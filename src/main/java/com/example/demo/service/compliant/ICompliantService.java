package com.example.demo.service.compliant;

import com.example.demo.dto.compliant.CompliantResponse;
import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.UpdateRequest;
import com.example.demo.model.Compliant;

import java.util.List;


public interface ICompliantService {
   public CompliantResponse createCompliant(CreateRequest createRequest);
   public List<CompliantResponse> getAllComplaints();
   public CompliantResponse getCompliantById(long id);
   public CompliantResponse updateCompliant(long id, UpdateRequest updateRequest);
   public CompliantResponse updateStatus(long id, com.example.demo.enums.Status status);
   public void deleteCompliant(long id);
   public CompliantResponse getCompliantByReference(String referenceNumber);
}
