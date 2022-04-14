package com.gcb.DoctorsService.entity;

import com.gcb.DoctorsService.entity.Doctor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "doctor_specialty")
public class DoctorSpecialty {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "doctor_crm")
    private Doctor doctor;
    
    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;
}
