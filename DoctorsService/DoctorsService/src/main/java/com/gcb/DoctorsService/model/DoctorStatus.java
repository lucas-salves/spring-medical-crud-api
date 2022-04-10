package com.gcb.DoctorsService.model;

public class DoctorStatus {
    
    public static enum Status{
        ACTIVE("active"), 
        DELETED("deleted");
        
        private String value;

        private Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
    
}
