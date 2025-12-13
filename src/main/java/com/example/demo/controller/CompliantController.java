package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.UpdateRequest;
import com.example.demo.model.Compliant;
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
    public ApiResponse<Compliant> createCompliant(@ModelAttribute CreateRequest createRequest){
         Compliant createdCompliant = compliantService.createCompliant(createRequest);
         return new ApiResponse<>(true, "Complaint created successfully", createdCompliant);
    }

    @GetMapping("/")
    public ApiResponse<List<Compliant>> getAllComplaints(){
        List<Compliant> complaints = compliantService.getAllComplaints();
        return new ApiResponse<>(true, "Complaints retrieved successfully", complaints);
    }

    @GetMapping("/{id}")
    public ApiResponse<Compliant> getCompliantById(@PathVariable int id){
        Compliant compliant = compliantService.getCompliantById(id);
        return new ApiResponse<>(true, "Complaint retrieved successfully", compliant);
    }

    @PutMapping("/{id}")
    public ApiResponse<Compliant> updateCompliant(@PathVariable int id, @ModelAttribute UpdateRequest updateRequest){
        Compliant updatedCompliant = compliantService.updateCompliant(id, updateRequest);
        return new ApiResponse<>(true, "Complaint updated successfully", updatedCompliant);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCompliant(@PathVariable int id){
        compliantService.deleteCompliant(id);
        return new ApiResponse<>(true, "Complaint deleted successfully", null);
    }

}
