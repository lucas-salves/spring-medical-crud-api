package com.gcb.DoctorsService.entity;

import com.gcb.DoctorsService.model.MedicalSpecialty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "specialty")
public class Specialty {
    
    @Id
    private long id;
    
    @Column(nullable = false, unique = false)
    private MedicalSpecialty specialtyName;
}
