package com.gcb.DoctorsService.repository;

import com.gcb.DoctorsService.entity.Doctor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, String>{

    @Query(value = "SELECT * FROM doctor d WHERE d.status = 'ACTIVE'", nativeQuery = true)
    List<Doctor> findAllActiveDoctors();
    
    @Query(value = "SELECT * FROM doctor d WHERE d.crm = ?1 AND d.status = 'ACTIVE'", nativeQuery = true)
    Doctor findActiveDoctorById(String crm);
    
    @Query(value = "SELECT * FROM doctor d WHERE MATCH(d.crm,d.address_complement,d.city,d.created_at,d.landline,d.mobile_phone,d.name,d.neighborhood,d.postal_code,d.status,d.street,d.uf) AGAINST (?1 IN BOOLEAN MODE)", nativeQuery = true)
    List<Doctor> searchDoctor(String input);
}
