package com.gcb.DoctorsService.controller;

import com.gcb.DoctorsService.entity.Doctor;
import com.gcb.DoctorsService.interactor.DoctorCreator;
import com.gcb.DoctorsService.model.DeleteDoctorRequest;
import com.gcb.DoctorsService.model.DoctorRequest;
import com.gcb.DoctorsService.model.Feed;
import com.gcb.DoctorsService.repository.DoctorRepository;
import com.gcb.DoctorsService.repository.DoctorSpecialtyRepository;
import com.gcb.DoctorsService.repository.SpecialtyRepository;
import com.gcb.DoctorsService.service.AMQPPublisher;
import com.gcb.DoctorsService.util.DoctorUtil;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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

    @Autowired
    private DoctorCreator doctorCreator;
    
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @GetMapping(value = "/get")
    public ResponseEntity<Doctor> getDoctorById(@RequestParam("doctorId") String id) throws Exception {
            
        Doctor doctor = new Doctor();

        try {

            if(id.equals("") || id == null)
                throw new Exception("Err 1.doctorId: Parâmetro doctorId obrigatório");
            

            doctor = repository.findActiveDoctorById(id);

            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (Exception e) {

            if(e.getMessage().contains("Err 1."))
                return new ResponseEntity<>(doctor, HttpStatus.BAD_REQUEST);
            
            return new ResponseEntity<>(doctor, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Doctor>> listAll() throws Exception {

        try {

            var doctors = repository.findAllActiveDoctors();

            return new ResponseEntity<>(doctors, HttpStatus.OK);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
    
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @GetMapping(value = "/search")
    public ResponseEntity<List<Doctor>> search(@RequestParam String input) throws Exception {

        List<Doctor> doctors = null;

        try {
            
            doctors = repository.searchDoctor(input);

            if (doctors == null) {
                throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (HttpClientErrorException ex) {

            if (ex.getRawStatusCode() == 400) {
                return new ResponseEntity<>(doctors, HttpStatus.BAD_REQUEST);
            }

            if (ex.getRawStatusCode() == 500) {
                return new ResponseEntity<>(doctors, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            throw new HttpClientErrorException(ex.getStatusCode(), ex.getMessage());
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "204", description = "Deletado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @PutMapping(value = "/delete")
    public ResponseEntity<Doctor> delete(@RequestBody DeleteDoctorRequest body) {

        var doctor = repository.findActiveDoctorById(body.getCrm());

        doctor.setStatus("Deleted");

        try {
            
            if(body.getCrm().equals("") || body.getCrm() == null)
                throw new Exception("Err 1.CRM: Campo CRM obrigatório");
            
            var response = repository.save(doctor);
            
        } catch (Exception e) {
            if(e.getMessage().contains("Err 1."))
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

    }

    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @ApiResponse(responseCode = "201", description = "Feed gerado com sucesso. Aguarde o processamento do update. Utilize o endpoint /feed para checar o status do Feed")
    @PutMapping(value = "/update")
    public ResponseEntity<Feed> update(@RequestBody DoctorRequest body) throws URISyntaxException, Exception {

        if (body.getSpecialtiesId().size() < 2) {

            Feed feedError = new Feed();

            feedError.setFeedErrors(true);

            feedError.setMessageError("O número mínimo de especialidades é 2.");

            return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
        }

        var json = gson.toJson(body);

        if (body.getPostalCode() != null) {

            try {

                var publisher = new AMQPPublisher();

                publisher.sendToQueue("doctor_address", json);

                DoctorCreator doctorCreator = new DoctorCreator();

                var feed = doctorCreator.generateFeed(body.getCrm(), "create");

                return feed;

            } catch (Exception e) {

                Feed feedError = new Feed();

                feedError.setFeedErrors(true);

                feedError.setMessageError(e.getMessage());

                return new ResponseEntity<Feed>(feedError, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        }

        DoctorCreator doctorCreator = new DoctorCreator();

        var doctor = DoctorCreator.doctorConverter(body);

        repository.save(doctor);

        Feed feed = new Feed();

        doctorCreator.generateFeed(doctor.getCrm(), "update");

        feed.setRequestAction("update");

        return new ResponseEntity<Feed>(feed, HttpStatus.OK);

    }

    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @ApiResponse(responseCode = "201", description = "Feed gerado com sucesso. Aguarde o processamento do create. Utilize o endpoint /feed para checar o status do Feed")
    @PostMapping(value = "/asyncCreate")
    public ResponseEntity<Feed> asynCreate(@RequestBody DoctorRequest requestBody) throws Exception {
        Feed feedError = new Feed();

        var json = gson.toJson(requestBody);

        try {

            if (requestBody.getSpecialtiesId().size() < 2) {
                throw new Exception("ERR: 1.O número minimo de especialidades é 2. Payload: "+requestBody.getSpecialtiesId().size()+" especialidades.");
            }

            if (requestBody.getName().length() > 120) {
                throw new Exception("ERR: 2.Name: atributo Name acima de 120 caracteres. Payload: "+requestBody.getName()+". Número de caracteres: "+requestBody.getName().length());
            }
            
            if(requestBody.getCrm().length() > 7){
                throw new Exception("ERR: 3.CRM: atributo CRM acima de 7 caracteres. Payload: "+requestBody.getCrm()+". Número de caracteres: "+requestBody.getCrm().length());
            }
            
            if( !(requestBody.getCrm().matches("[0-9]+"))){
                throw new Exception("ERR: 4.CRM: atributo CRM só pode conter apenas números. Payload: "+requestBody.getCrm());
            }
            
            if( !(requestBody.getLandline().matches("[0-9]+")) ){
                throw new Exception("ERR: 5.Landline: atributo Landline só pode conter apenas números. Payload: "+requestBody.getLandline());
            }
            
            if( !(requestBody.getMobilePhone().matches("[0-9]+")) ){
                throw new Exception("ERR: 6.mobilePhone: atributo mobilePhone só pode conter apenas números. Payload: "+requestBody.getMobilePhone());
            }
            
            if( !(requestBody.getPostalCode().matches("[0-9]+")) ){
                throw new Exception("ERR: 7.postalCode: atributo postalCode só pode conter apenas números. Payload: "+requestBody.getPostalCode());
            }

            if( requestBody.getLandline().length() != 10 ){
                throw new Exception("ERR: 8.Landline: atributo Landline precisa conter 10 caracteres. Exemplo: 1156478941. Payload: "+requestBody.getLandline());
            }
            
            if( requestBody.getMobilePhone().length() != 11 ){
                throw new Exception("ERR: 9.mobilePhone: atributo mobilePhone precisa conter 11 caracteres. Exemplo: 11952230505. Payload: "+requestBody.getMobilePhone());
            }
            
            var publisher = new AMQPPublisher();

            publisher.sendToQueue("doctor_address", json);

            var requestId = DoctorUtil.hashMD5(requestBody.getCrm());

            var feed = doctorCreator.generateFeed(requestId, "create");

            return feed;

        } catch (Exception ex) {

            feedError.setFeedErrors(true);

            feedError.setMessageError(ex.getMessage());

            if (ex.getMessage().contains("ERR: 1.")) {
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            
            if(ex.getMessage().contains("ERR: 2.")){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            
            if(ex.getMessage().contains("ERR: 3.")){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            
            if( ex.getMessage().contains("ERR: 4.") ){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            if( ex.getMessage().contains("ERR: 5.") ){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            if( ex.getMessage().contains("ERR: 6.") ){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            if( ex.getMessage().contains("ERR: 7.") ){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            if( ex.getMessage().contains("ERR: 8.") ){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            if( ex.getMessage().contains("ERR: 9.") ){
                return new ResponseEntity<Feed>(feedError, HttpStatus.BAD_REQUEST);
            }
            
            Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);
            
            return new ResponseEntity<Feed>(feedError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> create(@RequestBody DoctorRequest requestBody) throws Exception {

        try {
            
            doctorCreator.createDoctorUseCase(requestBody);

            
        } catch (Exception ex) {
            Logger.getLogger(DoctorController.class.getName()).log(Level.SEVERE, null, ex);

            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        

    }
}
