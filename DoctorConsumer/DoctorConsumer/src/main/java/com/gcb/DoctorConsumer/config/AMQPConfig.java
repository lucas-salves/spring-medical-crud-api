package com.gcb.DoctorConsumer.config;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AMQPConfig {
    
    private static final String URI = "AWS URI";

    private static final String USERNAME = "rabbitUser";

    private static final String PASSWORD = "rabbitPassword";

    public static Connection getConnection() {
        
        Connection connection = null;
        
        try {
            var factory = new ConnectionFactory();
            
            factory.setUri(URI);
            factory.setConnectionTimeout(5000);
            factory.setRequestedHeartbeat(5);
            factory.setUsername(USERNAME);
            factory.setPassword(PASSWORD);
            
            var exec = Executors.newCachedThreadPool();
            
            connection = factory.newConnection(exec);
            
            return connection;
        } catch (IOException ex) {
            Logger.getLogger(AMQPConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(AMQPConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AMQPConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AMQPConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(AMQPConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
}
