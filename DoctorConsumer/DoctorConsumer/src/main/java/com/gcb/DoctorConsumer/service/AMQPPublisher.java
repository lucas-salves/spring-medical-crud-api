package com.gcb.DoctorConsumer.service;

import com.gcb.DoctorConsumer.config.AMQPConfig;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AMQPPublisher {

    private Connection connection;

    private Channel channel;

    public AMQPPublisher() {
        try {
            connection = AMQPConfig.getConnection();
            channel = connection.createChannel();
        } catch (IOException ex) {
            Logger.getLogger(AMQPPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendToQueue(String queue, String message) throws IOException {
        if (connection == null || channel == null) {
            connection = AMQPConfig.getConnection();
            channel = connection.createChannel();
        }

        //queue, durable, exclusive, autoDelete, properties
        channel.queueDeclare(queue, true, false, false, null);

        channel.basicPublish("", queue, null, message.getBytes("UTF-8"));
    }

    public void close() {
        //replace with a try-with-resources later
        try {
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}