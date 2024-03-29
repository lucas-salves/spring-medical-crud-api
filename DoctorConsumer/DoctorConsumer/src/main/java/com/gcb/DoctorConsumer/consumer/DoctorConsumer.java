package com.gcb.DoctorConsumer.consumer;

import com.gcb.DoctorConsumer.interactor.AddressCreator;
import com.gcb.DoctorConsumer.model.Doctor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class DoctorConsumer extends DefaultConsumer {

    private final Channel channel;

    private final Gson gson = new Gson();
    
    @Autowired
    private RestTemplate restTemplate;
    

    
    
    public DoctorConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String csTag, Envelope env, AMQP.BasicProperties props, byte[] body) {
        try {

            var message = new String(body, "UTF-8");

            this.processDelivery(message);

            //tag, ack all messages
            channel.basicAck(env.getDeliveryTag(), false);

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);

        } catch (Exception ex) {
            Logger.getLogger(DoctorConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processDelivery(String message) throws Exception {
        System.out.println(message);

        var doctor = gson.fromJson(message, Doctor.class);
        
        AddressCreator addressCreator = new AddressCreator();
        
        try {
            addressCreator.getAddress(doctor);
        }catch(Exception ex){
            addressCreator.updateStatus(doctor.getCrm(),
                    "Error", ex.getMessage());
        }

    }
}
