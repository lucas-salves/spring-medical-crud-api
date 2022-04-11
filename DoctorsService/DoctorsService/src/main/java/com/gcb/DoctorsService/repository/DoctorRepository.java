package com.gcb.DoctorsService.repository;

import com.gcb.DoctorsService.entity.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, String>{

    
}
