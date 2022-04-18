package com.gcb.DoctorsService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor {

    public Doctor() {
    }
    
    

    public Doctor(String crm, String name, String landline, String mobilePhone, String postalCode, String addressComplement, String street, String neighborhood, String city, String uf, String status, String createdAt) {
        this.crm = crm;
        this.name = name;
        this.landline = landline;
        this.mobilePhone = mobilePhone;
        this.postalCode = postalCode;
        this.addressComplement = addressComplement;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    @Id
    @Column(length = 7, nullable = false, unique = true)
    @JsonProperty("crm")
    private String crm;

    @JsonProperty("name")
    @Column(length = 120, nullable = false, unique = false)
    private String name;

    @Column(length = 10, nullable = true, unique = false)
    @JsonProperty("landline")
    private String landline;

    @Column(length = 11, nullable = true, unique = false)
    @JsonProperty("mobilePhone")
    private String mobilePhone;

    @Column(length = 8, nullable = false, unique = false)
    @JsonProperty("postalCode")
    private String postalCode;

    @Column(nullable = true)
    @JsonProperty("addressComplement")
    private String addressComplement;

    @Column(nullable = true)
    @JsonProperty("street")
    private String street;

    @Column(nullable = true)
    @JsonProperty("neighborhood")
    private String neighborhood;

    @Column(nullable = true)
    @JsonProperty("city")
    private String city;

    @Column(nullable = true)
    @JsonProperty("uf")
    private String uf;
    
    @JsonBackReference
    @OneToMany(mappedBy = "doctor")
    private Set<DoctorSpecialty> doctorSpecialties;

    @Column(nullable = false)
    @JsonProperty("status")
    private String status;

    @Column(nullable = true)
    @JsonProperty("createdAt")
    private String createdAt=null;
}
