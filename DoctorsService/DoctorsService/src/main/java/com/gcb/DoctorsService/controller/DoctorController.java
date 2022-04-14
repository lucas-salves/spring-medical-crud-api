package com.gcb.DoctorsService.controller;

import com.gcb.DoctorsService.entity.Doctor;
import com.gcb.DoctorsService.interactor.DoctorCreator;
import com.gcb.DoctorsService.model.DoctorRequest;
import com.gcb.DoctorsService.repository.DoctorRepository;
import com.gcb.DoctorsService.repository.DoctorSpecialtyRepository;
import com.gcb.DoctorsService.repository.SpecialtyRepository;
import com.gcb.DoctorsService.service.AMQPPublisher;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final Gson gson = new Gson();

    @Autowired
    private DoctorRepository repository;
    
    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    @GetMapping(value = "/get")
    public ResponseEntity<Doctor> getDoctorById(@RequestParam("doctorId") String id) throws Exception {

        try {

            var doctor = repository.findById(id).get();

            return new ResponseEntity<>(doctor, HttpStatus.OK);

        } catch (Exception e) {

            throw new Exception(e.getMessage());
        }
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Doctor>> listAll() throws Exception {

        try {

            Iterable doctorsResponse = repository.findAll();

            List<Doctor> doctors = ImmutableList.copyOf(doctorsResponse);

            return new ResponseEntity<>(doctors, HttpStatus.OK);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
    
    @PostMapping(value = "/update")
    public ResponseEntity<String> update(@RequestBody DoctorRequest body){
        
        if( body.getSpecialtiesId().size() < 2 ){
            return new ResponseEntity<String>("O número mínimo de especialidades é 2.", HttpStatus.BAD_REQUEST);
        }
        
        var json = gson.toJson(body);
        
        if(body.getPostalCode() != null){
            
            try {
                
                var publisher = new AMQPPublisher();
                
                publisher.sendToQueue("doctor_address", json);
                
                return new ResponseEntity<String>("Em processamento.", HttpStatus.ACCEPTED);
                
            } catch (Exception e) {
                return new ResponseEntity<String>("Erro; " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
        }
        
        var crm = body.getCrm();
        var name = body.getName();
        var landline = body.getLandline();
        var mobilePhone = body.getMobilePhone();
        var postalCode = body.getPostalCode();
        var addressComplement = body.getAddressComplement();
        var street = body.getStreet();
        var neighborhood = body.getNeighborhood();
        var city = body.getCity();
        var uf = body.getUf();
        var status = body.getStatus();
        var createdAt = body.getCreatedAt();
        
        Doctor doctor = new Doctor(
            crm,
            name,
            landline,
            mobilePhone,
            postalCode,
            addressComplement,
            street,
            neighborhood,
            city,
            uf, 
            status,
            createdAt);
        
        repository.save(doctor);
        
        return new ResponseEntity<String>("Atualizado.", HttpStatus.OK);
        
    }

    @PostMapping(value = "/asyncCreate")
    public ResponseEntity<String> asynCreate(@RequestBody DoctorRequest requestBody) {

        if (requestBody.getSpecialtiesId().size() < 2) {
            return new ResponseEntity<String>("O número minimo de especialidades é 2.", HttpStatus.BAD_REQUEST);
        }

        var json = gson.toJson(requestBody);

        try {
            var publisher = new AMQPPublisher();
            
            publisher.sendToQueue("doctor_address", json);

            return new ResponseEntity<String>("Em processamento.", HttpStatus.ACCEPTED);

        } catch (Exception ex) {

            Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);

            return new ResponseEntity<String>("Erro: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody DoctorRequest requestBody) throws Exception {
        DoctorCreator doctorCreator = new DoctorCreator();
        
        
        try {
            doctorCreator.createDoctorUseCase(requestBody);
            
            return null;
        } catch (Exception ex) {
            Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new Exception(ex.getMessage());
        }
        
        
    }
}
