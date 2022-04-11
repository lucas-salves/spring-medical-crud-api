package com.gcb.DoctorsService.service;

import com.gcb.DoctorsService.entity.Doctor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


public interface DoctorService {
    public ResponseEntity<Object> create(@RequestBody Doctor doctorRequest);
}
