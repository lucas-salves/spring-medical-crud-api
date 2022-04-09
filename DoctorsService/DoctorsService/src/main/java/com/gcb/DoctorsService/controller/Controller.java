package com.gcb.DoctorsService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctor")
public class Controller {
    
    public ResponseEntity<Object> create(@RequestBody String json){
        return null;
    }
}
