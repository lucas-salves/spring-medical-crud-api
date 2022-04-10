package com.gcb.DoctorsService.entity;

import com.gcb.DoctorsService.model.DoctorStatus;
import com.google.gson.annotations.SerializedName;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {
    
    @Id
    @Column(length = 7, nullable = false, unique = true)
    @SerializedName("crm")
    private String crm;
    
    @SerializedName("name")
    private String name;
    
    @Column(length = 10, nullable = true, unique = false)
    @SerializedName("landline")
    private String landline;
    
    @Column(length = 11, nullable = true, unique = false)
    @SerializedName("mobilePhone")
    private String mobilePhone;
    
    @Column(length = 8, nullable = false, unique = false)
    @SerializedName("postalCode")
    private String postalCode;
    
    @Column(nullable = false)
    @SerializedName("addressComplement")
    private String addressComplement;
    
    @Column(nullable = false)
    @SerializedName("street")
    private String street;
    
    @Column(nullable = false)
    @SerializedName("neighborhood")
    private String neighborhood;
    
    @Column(nullable = false)
    @SerializedName("city")
    private String city;
    
    @Column(nullable = false)
    @SerializedName("uf")
    private String uf;
    
    @SerializedName("specialty")
    @Column(nullable = false)
    @ManyToMany
    @JoinColumn(name = "specialty_id")
    private Specialty medicalSpecialty;
    
    @Column(nullable = false)
    @SerializedName("status")
    private DoctorStatus.Status status = DoctorStatus.Status.ACTIVE;
    
    @Column(nullable = false)
    @SerializedName("createdAt")
    private Date createdAt;
}
