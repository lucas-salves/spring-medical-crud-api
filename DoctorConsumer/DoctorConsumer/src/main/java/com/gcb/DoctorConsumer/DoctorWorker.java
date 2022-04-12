package com.gcb.DoctorConsumer;

import com.gcb.DoctorConsumer.config.AMQPConfig;
import com.gcb.DoctorConsumer.consumer.DoctorConsumer;

public class DoctorWorker {

    private static final String QUEUE_NAME = "save_doctor";

    public static void main(String[] args) throws Exception {

        var connection = AMQPConfig.getConnection();

        var channel = connection.createChannel();

        channel.basicQos(0);

        //queue, durable, exclusive, autoDelete, properties
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        //requeue
        channel.basicRecover(true);

        var callback = new DoctorConsumer(channel);

        //queue, autoAck, callback
        channel.basicConsume(QUEUE_NAME, false, callback);

        System.out.println("waiting message");
    }
}
