package com.gcb.DoctorsService.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = true)
    @SerializedName("street")
    private String street;

    @Column(nullable = true)
    @SerializedName("neighborhood")
    private String neighborhood;

    @Column(nullable = true)
    @SerializedName("city")
    private String city;

    @Column(nullable = true)
    @SerializedName("uf")
    private String uf;

    @Column(nullable = false)
    @SerializedName("status")
    private String status;

    @Column(nullable = true)
    @SerializedName("createdAt")
    private String createdAt="";

    @SerializedName("specialtiesId")
    private List<Long> specialtiesId;
}
