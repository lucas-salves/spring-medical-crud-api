package com.gcb.DoctorsService.repository;

import com.gcb.DoctorsService.entity.Specialty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends CrudRepository<Specialty, Long>{
    
}
