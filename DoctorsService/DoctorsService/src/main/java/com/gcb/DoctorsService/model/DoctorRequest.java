package com.gcb.DoctorsService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gcb.DoctorsService.entity.Doctor;
import com.gcb.DoctorsService.entity.DoctorSpecialty;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
public class DoctorRequest {

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

    @Column(nullable = false)
    @JsonProperty("street")
    private String street;

    @Column(nullable = false)
    @JsonProperty("neighborhood")
    private String neighborhood;

    @Column(nullable = false)
    @JsonProperty("city")
    private String city;

    @Column(nullable = false)
    @JsonProperty("uf")
    @NonNull
    private String uf;

    @Column(nullable = false)
    @JsonProperty("status")
    private String status;

    @Column(nullable = false)
    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("specialtiesId")
    private List<Long> specialtiesId;
}
