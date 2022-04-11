package com.gcb.DoctorsService.controller;

import com.gcb.DoctorsService.entity.Doctor;
import com.gcb.DoctorsService.entity.DoctorSpecialty;
import com.gcb.DoctorsService.entity.Specialty;
import com.gcb.DoctorsService.model.DoctorRequest;
import com.gcb.DoctorsService.model.MedicalSpecialty;
import com.gcb.DoctorsService.repository.DoctorRepository;
import com.gcb.DoctorsService.repository.DoctorSpecialtyRepository;
import com.gcb.DoctorsService.repository.SpecialtyRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class Controller {
    
    @Autowired
    private DoctorRepository repository;
    
    @Autowired
    private SpecialtyRepository specialtyRepository;
    
    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;
    
    @GetMapping(value = "/get")
    public void index(){
        var alergo = MedicalSpecialty.ALERGOLOGIA.getValue();
        var angio = MedicalSpecialty.ANGIOLOGIA.getValue();
        
        Specialty especialidade1 = new Specialty();
        Specialty especialidade2 = new Specialty();
        especialidade1.setSpecialtyName(angio);
        especialidade2.setSpecialtyName(alergo);
        
        specialtyRepository.save(especialidade1);
        specialtyRepository.save(especialidade2);
    }
    
    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody DoctorRequest requestBody){
        var specialtiesId = requestBody.getSpecialtiesId();
        
        String crm = requestBody.getCrm();
        String name = requestBody.getName();
        String landline = requestBody.getLandline();
        String mobilePhone = requestBody.getMobilePhone();
        String postalCode = requestBody.getPostalCode();
        String complement = requestBody.getAddressComplement();
        String street = requestBody.getStreet();
        String neighborhood = requestBody.getNeighborhood();
        String city = requestBody.getCity();
        String uf = requestBody.getUf();
        String status = requestBody.getStatus();
        String createdAt = requestBody.getCreatedAt();
        
        Doctor doctor = new Doctor(
                crm,
                name,
                landline,
                mobilePhone,
                postalCode, 
                complement, 
                street, 
                neighborhood, 
                city, 
                uf, 
                status, 
                createdAt
        );
        
        
        repository.save(doctor);
        
        
        var specialtyIterable = specialtyRepository.findAll();
        
        List<Specialty> specialtyList = new ArrayList<>();
        specialtyIterable.forEach(specialtyList::add);
  
        List<Specialty> resultSpecialties = new ArrayList<>();
        for(Specialty item : specialtyList){
            for(Long selectedItem : specialtiesId){
                if(item.getId().equals(selectedItem)){
                    Specialty specialty = new Specialty();
                    specialty.setId(item.getId());
                    specialty.setSpecialtyName(item.getSpecialtyName());
                    resultSpecialties.add(specialty);
                }
            }
        }
        
        List<DoctorSpecialty> idList = new ArrayList<>();
        
        var index = 0L;
        for(Specialty item : resultSpecialties){
            DoctorSpecialty ds = new DoctorSpecialty();
            ds.setId(index);
            ds.setDoctor(doctor);
            ds.setSpecialty(item);
            idList.add(ds);
            index++;
        }
        
        for(var item : idList){
            doctorSpecialtyRepository.save(item);
        }
        
        return null;
    }
}
