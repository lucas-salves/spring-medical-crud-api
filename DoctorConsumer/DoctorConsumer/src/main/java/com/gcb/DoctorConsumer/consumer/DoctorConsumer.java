package com.gcb.DoctorConsumer.consumer;

import com.gcb.DoctorConsumer.model.Doctor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DoctorConsumer extends DefaultConsumer{

    private final Channel channel;

    private final Gson gson = new Gson();

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
        
        //send to the addressmicroservice
    }
}
