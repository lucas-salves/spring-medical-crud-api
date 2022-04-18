package com.gcb.DoctorsService.entity;

import com.gcb.DoctorsService.entity.DoctorSpecialty;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "specialty")
public class Specialty {

    public Specialty() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = false)
    private String specialtyName;

    @OneToMany(mappedBy = "specialty")
    private Set<DoctorSpecialty> doctorSpecialties;
}
