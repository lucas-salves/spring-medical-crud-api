package com.gcb.DoctorConsumer.interactor;

import com.gcb.DoctorConsumer.model.Address;
import com.gcb.DoctorConsumer.model.Doctor;
import com.gcb.DoctorConsumer.model.Feed;
import com.gcb.DoctorConsumer.util.ConsumerUtil;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressCreator {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Address> getAddress(Doctor doctor) throws Exception {
        var feedId = doctor.getCrm();
        try {

            AddressCreator addressCreator = new AddressCreator();

            addressCreator.updateStatus(feedId, "Processing", "");

        } catch (RestClientException restClientException) {
            throw new RestClientException(restClientException.getMessage());
        }

        String baseUrl = "http://localhost:8083/address/get";

        ResponseEntity<Address> response = null;
        
        try {
            response = new RestTemplate().exchange(baseUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(doctor),
                    Address.class);
            if(response == null)
                throw new Exception("Não foi possível obter o endereço. Erro desconhecido.");
            
            if(response.getStatusCode().equals(200)  && response.getBody().getStreet() == null)
                throw new Exception("CEP inválido: Esse CEP não existe.");
        } catch (HttpClientErrorException  restClientException) {
            
            if(restClientException.getRawStatusCode() == 400)
                throw new Exception("Code: 400, Message: CEP inválido: Esse CEP não existe ou é inválido.");
            
            throw new Exception("Code: "+ restClientException.getRawStatusCode()
                    +", Message: "+restClientException.getMessage());
        }

        return response;
    }

    public ResponseEntity<Feed> updateStatus(String feedId, String status, String errorMessage) throws RestClientException, Exception {
        
        feedId = ConsumerUtil.hashMD5(feedId);
        
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
