package com.example.demo.service.compliant;

import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.UpdateRequest;
import com.example.demo.model.Compliant;

import java.util.List;


public interface ICompliantService {
   public Compliant createCompliant(CreateRequest createRequest);
   public List<Compliant> getAllComplaints();
   public Compliant getCompliantById(int id);
   public Compliant updateCompliant(int id, UpdateRequest updateRequest);
   public Compliant updateStatus(int id, com.example.demo.enums.Status status);
   public void deleteCompliant(int id);
   public Compliant getCompliantByReference(String referenceNumber);
}
