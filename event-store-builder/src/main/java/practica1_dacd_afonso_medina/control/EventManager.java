package practica1_dacd_afonso_medina.control;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.ReceiveException;

import javax.jms.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EventManager {

    private static String url = "tcp://localhost:61616";
    private static String subject = "prediction.Weather";

    public void eventReceiver() throws ReceiveException{
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("Lucia");
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(subject);
            MessageConsumer consumer = session.createDurableSubscriber(topic, "Lucia");
            consumer.setMessageListener(message -> {
                try {
                    System.out.println("Message Receive: " + ((TextMessage) message).getText());
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            });
        }catch (JMSException e){
            throw new ReceiveException(e.getMessage());
        }
    }
}
