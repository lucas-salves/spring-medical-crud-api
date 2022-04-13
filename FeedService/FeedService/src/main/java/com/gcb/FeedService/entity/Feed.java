package com.gcb.FeedService.entity;

import com.google.gson.annotations.SerializedName;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "feed")
public class Feed {
    @Id
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
