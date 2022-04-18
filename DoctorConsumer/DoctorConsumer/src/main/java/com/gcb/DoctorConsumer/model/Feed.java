package com.gcb.DoctorConsumer.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
public class Feed {

    public Feed() {
    }

    public Feed(String requestId, String requestAction, String status, String createdAt, String updatedAt) {
        this.requestId = requestId;
        this.requestAction = requestAction;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Feed(String requestId, String requestAction, String status, String createdAt, String updatedAt, boolean feedErrors, String messageError) {
        this.requestId = requestId;
        this.requestAction = requestAction;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.feedErrors = feedErrors;
        this.messageError = messageError;
    }
    
    
    
    
    @SerializedName("requestId")
    private String requestId;
    
    
    @SerializedName("requestAction")
    private String requestAction;
    
    
    @SerializedName("status")
    private String status;
    
    @SerializedName("createdAt")
    private String createdAt;
    
    
    @SerializedName("updatedAt")
    private String updatedAt;
    
    @SerializedName("feedErrors")
    private boolean feedErrors;
    
    @SerializedName("messageError")
    private String messageError;
    
}
