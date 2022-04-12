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
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {

    @Id
    @Column(length = 7, nullable = false, unique = true)
    @SerializedName("crm")
    private String crm;

    @SerializedName("name")
    @Column(length = 120, nullable = false, unique = false)
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

    @Column(nullable = true)
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
    @NonNull
    private String uf;

    @Column(nullable = false)
    @SerializedName("status")
    private String status;

    @Column(nullable = false)
    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("specialtiesId")
    private List<Long> specialtiesId;
}
