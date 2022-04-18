package com.gcb.AddressService.interactor;

import com.gcb.AddressService.model.Doctor;
import com.gcb.AddressService.model.DoctorRequest;
import com.gcb.AddressService.model.Feed;
import com.gcb.AddressService.util.AddressUtil;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressCreator {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<DoctorRequest> createDoctor(Doctor body) throws Exception {

        String baseUrl = "http://localhost:8081/doctor/create";

        ResponseEntity<DoctorRequest> response = null;

        try {

            response = new RestTemplate().exchange(baseUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(body),
                    DoctorRequest.class);

        } catch (RestClientException restClientException) {
            throw new RestClientException(restClientException.getMessage());
        }

        AddressCreator addressCreator = new AddressCreator();

        try {

            var resp = addressCreator.updateStatus(body.getCrm(), "Finished","");
            
        } catch (RestClientException restClientException) {
            throw new RestClientException(restClientException.getMessage());
        }

        return response;
    }

    public ResponseEntity<Feed> updateStatus(String feedId, String status, String errorMessage) throws RestClientException, Exception {
        feedId = AddressUtil.hashMD5(feedId);
        
        Feed updateFeed = null;
        
        if (errorMessage.equals("")) {
            updateFeed = new Feed(feedId, "Patch", status, null, new Date().toString());
        }
        
        if (!errorMessage.equals("")) {
            updateFeed = new Feed(feedId, "Patch", status, null, new Date().toString(),true, errorMessage);
        }


        var feedUpdateUrl = "http://localhost:8084/feed/update";

        var updateResponse = new RestTemplate().exchange(feedUpdateUrl,
                HttpMethod.PUT,
                new HttpEntity<>(updateFeed),
                Feed.class);

        return updateResponse;

    }

}
