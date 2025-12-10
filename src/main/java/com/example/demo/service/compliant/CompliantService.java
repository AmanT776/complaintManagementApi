package com.example.demo.service.compliant;

import com.example.demo.dto.compliant.CompliantRequest;
import com.example.demo.model.Compliant;
import com.example.demo.repository.CompliantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompliantService implements ICompliantService {

    @Autowired
    private CompliantRepository compliantRepository;

    @Autowired
    private ModelMapper mapper;

   public void createCompliant(CompliantRequest compliantRequest){
       Compliant compliant = mapper.map(compliantRequest,Compliant.class);
       compliantRepository.save(compliant);
    }
}
