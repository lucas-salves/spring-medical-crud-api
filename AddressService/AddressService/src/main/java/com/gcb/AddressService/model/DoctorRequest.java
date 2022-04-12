package com.gcb.AddressService.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {
    
    @SerializedName("crm")
    private String crm;

    @SerializedName("name")
    
    private String name;
    
    @SerializedName("landline")
    private String landline;
    
    @SerializedName("mobilePhone")
    private String mobilePhone;
    
    @SerializedName("postalCode")
    private String postalCode;
    
    @SerializedName("addressComplement")
    private String addressComplement;
    
    @SerializedName("street")
    private String street;
    
    @SerializedName("neighborhood")
    private String neighborhood;
    
    @SerializedName("city")
    private String city;
    
    @SerializedName("uf")
    private String uf;
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("specialtiesId")
    private List<Long> specialtiesId;
}
