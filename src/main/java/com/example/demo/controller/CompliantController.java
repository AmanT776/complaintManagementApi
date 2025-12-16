package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.compliant.CompliantResponse;
import com.example.demo.dto.compliant.CreateRequest;

import com.example.demo.dto.compliant.UpdateRequest;
import com.example.demo.dto.compliant.UpdateStatusRequest;
import com.example.demo.service.compliant.CompliantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/compliant")
public class CompliantController {

    @Autowired
    private CompliantService compliantService;


    @PostMapping("/")
    public ApiResponse<CompliantResponse> createCompliant(
            @ModelAttribute CreateRequest createRequest) {

        CompliantResponse response = compliantService.createCompliant(createRequest);
        return new ApiResponse<>(true, "Complaint created successfully", response);
    }

    @GetMapping("/")
    public ApiResponse<List<CompliantResponse>> getAllComplaints() {

        List<CompliantResponse> responses = compliantService.getAllComplaints();

        return new ApiResponse<>(true, "Complaints retrieved successfully", responses);
    }


      // GET COMPLAINT BY ID

    @GetMapping("/{id}")
    public ApiResponse<CompliantResponse> getCompliantById(@PathVariable long id) {

        CompliantResponse response = compliantService.getCompliantById(id);

        return new ApiResponse<>(true, "Complaint retrieved successfully", response);
    }

     //  GET COMPLAINT BY REFERENCE

    @GetMapping("/reference/{referenceNumber}")
    public ApiResponse<CompliantResponse> getCompliantByReference(@PathVariable String referenceNumber) {

        CompliantResponse response = compliantService.getCompliantByReference(referenceNumber);

        return new ApiResponse<>(true, "Complaint retrieved successfully", response);
    }

      // UPDATE COMPLAINT

    @PutMapping("/{id}")
    public ApiResponse<CompliantResponse> updateCompliant(@PathVariable long id, @ModelAttribute UpdateRequest updateRequest) {

        CompliantResponse response =
                compliantService.updateCompliant(id, updateRequest);

        return new ApiResponse<>(true, "Complaint updated successfully", response);
    }


     //  UPDATE STATUS

    @PatchMapping("/{id}/status")
    public ApiResponse<CompliantResponse> updateStatus(@PathVariable long id, @RequestBody UpdateStatusRequest request) {

        CompliantResponse response =
                compliantService.updateStatus(id, request.getStatus());

        return new ApiResponse<>(true, "Complaint status updated successfully", response);
    }


      // DELETE COMPLAINT

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCompliant(@PathVariable long id) {

        compliantService.deleteCompliant(id);

        return new ApiResponse<>(true, "Complaint deleted successfully", null);
    }
}
