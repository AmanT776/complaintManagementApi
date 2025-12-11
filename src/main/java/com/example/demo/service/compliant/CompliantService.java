package com.example.demo.service.compliant;

import com.example.demo.dto.compliant.CreateRequest;
import com.example.demo.dto.compliant.CreateResponse;
import com.example.demo.mapper.CompliantMapper;
import com.example.demo.model.Category;
import com.example.demo.model.Compliant;
import com.example.demo.model.OrganizationalUnit;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CompliantRepository;
import com.example.demo.repository.OrganizationalUnitRepository;
import com.example.demo.repository.UserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private CompliantMapper compliantMapper = Mappers.getMapper(CompliantMapper.class);


   public Compliant createCompliant(CreateRequest createRequest){
      Compliant compliant = compliantMapper.mapCompliantRequestToCompliant(createRequest);
      OrganizationalUnit organizationalUnit = organizationalUnitRepository.findById(createRequest.getOrganizationalUnitId()).orElseThrow(()->new RuntimeException("organizational unit not found"));
      compliant.setOrganizationalUnit(organizationalUnit);
      Category category = categoryRepository.findById(createRequest.getCategoryId()).orElseThrow(()->new RuntimeException("category not found"));
      compliant.setCategory(category);
      User user = userRepository.findById(createRequest.getUserId()).orElseThrow(()->new RuntimeException("user not found"));
      compliant.setUser(user);
      UUID reference_number = UUID.randomUUID();
      compliant.setReferenceNumber(reference_number.toString());
      return compliantRepository.save(compliant);
    }
}
