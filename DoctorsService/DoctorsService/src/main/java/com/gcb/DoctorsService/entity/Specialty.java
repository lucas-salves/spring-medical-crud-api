package com.gcb.DoctorsService.entity;

import com.gcb.DoctorsService.entity.DoctorSpecialty;
import com.gcb.DoctorsService.model.MedicalSpecialty;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "specialty")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = false)
    private String specialtyName = MedicalSpecialty.ALERGOLOGIA.getValue();

    @OneToMany(mappedBy = "specialty")
    private Set<DoctorSpecialty> doctorSpecialties;
}
