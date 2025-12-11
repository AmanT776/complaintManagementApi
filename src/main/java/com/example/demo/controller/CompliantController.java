package com.example.demo.controller;

import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.CreateResponse;
import com.example.demo.model.Compliant;
import com.example.demo.service.compliant.CompliantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/compliant")
public class CompliantController {

    @Autowired
    private CompliantService compliantService;

    @PostMapping("/")
    public Compliant createCompliant(@RequestBody CreateRequest createRequest){
        return compliantService.createCompliant((createRequest));
    }

}
