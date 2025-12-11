package com.example.demo.service.compliant;

import com.example.demo.dto.compliant.CompliantRequest;
import com.example.demo.mapper.CompliantMapper;
import com.example.demo.model.Category;
import com.example.demo.model.Compliant;
import com.example.demo.model.OrganizationalUnit;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CompliantRepository;
import com.example.demo.repository.OrganizationalUnitRepository;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompliantService implements ICompliantService {

    @Autowired
    private CompliantRepository compliantRepository;
    @Autowired
    private OrganizationalUnitRepository organizationalUnitRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private CompliantMapper compliantMapper = Mappers.getMapper(CompliantMapper.class);


   public void createCompliant(CompliantRequest compliantRequest){
      Compliant compliant = compliantMapper.mapCompliantRequestToCompliant(compliantRequest);
      OrganizationalUnit organizationalUnit = organizationalUnitRepository.findById(compliantRequest.getOrganizationalUnitId()).orElseThrow(()->new RuntimeException("organizational unit not found"));
      compliant.setOrganizationalUnit(organizationalUnit);
      Category category = categoryRepository.findById(compliantRequest.getCategoryId()).orElseThrow(()->new RuntimeException("category not found"));
      compliant.setCategory(category);
      compliantRepository.save(compliant);
    }
}
