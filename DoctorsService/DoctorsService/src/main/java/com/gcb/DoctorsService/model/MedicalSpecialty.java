package com.gcb.DoctorsService.model;

public enum MedicalSpecialty {
    ALERGOLOGIA("Alergologia"), 
    ANGIOLOGIA("Angiologia"), 
    BUCO_MAXILO("Buco maxilo"), 
    CARDIOLOGIA_CLÍNCA("Cardiologia clínca"), 
    CARDIOLOGIA_INFANTIL("Cardiologia infantil"), 
    CIRURGIA_CABEÇA_E_PESCOÇO("Cirurgia cabeça e pescoço"), 
    CIRURGIA_CARDÍACA("Cirurgia cardíaca"),
    CIRURGIA_DE_TÓRAX("Cirurgia de tórax");
    
    private String value;
    
    private MedicalSpecialty(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
}
