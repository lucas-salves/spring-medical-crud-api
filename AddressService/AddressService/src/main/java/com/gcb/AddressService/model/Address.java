
package com.gcb.AddressService.model;

import com.google.gson.annotations.SerializedName;
import com.netflix.discovery.provider.Serializer;
import lombok.Data;

@Data
public class Address {
    
    @SerializedName("cep")
    private String postalCode;
    @SerializedName("logradouro")
    private String street;
    
    @SerializedName("complemento")
    private String addressComplement;
    
    @SerializedName("bairro")
    private String neighborhood;
    
    @SerializedName("localidade")
    private String city;
    
    @SerializedName("uf")
    private String uf;
    
    @SerializedName("error")
    private Error error;

    @Data
    public class Error{
        
        @SerializedName("message")
        private String message;
    }
    
}
