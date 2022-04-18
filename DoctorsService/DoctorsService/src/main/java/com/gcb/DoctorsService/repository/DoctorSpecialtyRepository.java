/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gcb.DoctorsService.repository;

import com.gcb.DoctorsService.entity.DoctorSpecialty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lucas
 */
@Repository
public interface DoctorSpecialtyRepository extends CrudRepository<DoctorSpecialty, Long>{
    
}
