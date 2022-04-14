package com.gcb.AddressService.controller;

import com.gcb.AddressService.model.Address;
import com.gcb.AddressService.model.DoctorRequest;
import com.google.gson.Gson;
import java.io.IOException;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/address")
public class AddressController {
    
    Gson gson = new Gson();
    
    @Autowired
    RestTemplate restTemplate;
    
    @PostMapping(value = "/get")
    public ResponseEntity<Address> getAddress(@RequestBody DoctorRequest requestBody) throws IOException{
        var postalCode = requestBody.getPostalCode();
        
        String json = "";
        
        Address address = new Address();
        
        try{
             json = restTemplate.getForObject("https://viacep.com.br/ws/"+postalCode+"/json/", String.class);
             
             address = gson.fromJson(json, Address.class);
             
        }catch(HttpClientErrorException ex){
            Address.Error error = address.new Error();
            
            error.setMessage(ex.getMessage());
            
            address.setError(error);
            
            return new ResponseEntity<>(address,HttpStatus.BAD_REQUEST);
        }
        
        
        if(address.getPostalCode() == null){
            Address.Error error = address.new Error();
            
            error.setMessage("Campo CEP obrigatório.");
            
            address.setError(error);
            
            return new ResponseEntity<>(address,HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}
