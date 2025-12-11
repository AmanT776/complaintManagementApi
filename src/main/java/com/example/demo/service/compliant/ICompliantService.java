package com.example.demo.service.compliant;

import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.CreateResponse;
import com.example.demo.model.Compliant;

public interface ICompliantService {
   public Compliant createCompliant(CreateRequest createRequest);
}
