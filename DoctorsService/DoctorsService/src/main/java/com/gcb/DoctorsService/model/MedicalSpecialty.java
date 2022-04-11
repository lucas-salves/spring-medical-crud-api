package com.gcb.DoctorsService.model;

public enum MedicalSpecialty {
    ALERGOLOGIA("Alergologia"), 
    ANGIOLOGIA("Angiologia"), 
    BUCO_MAXILO("Buco maxilo"), 
    CARDIOLOGIA_CLINCA("Cardiologia clínca"), 
    CARDIOLOGIA_INFANTIL("Cardiologia infantil"), 
    CIRURGIA_CABECA_E_PESCOCO("Cirurgia cabeça e pescoço"), 
    CIRURGIA_CARDIACA("Cirurgia cardíaca"),
    CIRURGIA_DE_TORAX("Cirurgia de tórax");
    
    private String value;
    
    private MedicalSpecialty(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
}
