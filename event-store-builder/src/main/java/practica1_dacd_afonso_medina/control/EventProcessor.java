package practica1_dacd_afonso_medina.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.ReceiveException;

import javax.jms.*;

public class EventProcessor {

    private static String url = "tcp://localhost:61616";
    private static String subject = "prediction.Weather";

    public void eventReceiver() throws ReceiveException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("Lucia");
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic(subject);
            MessageConsumer consumer = session.createDurableSubscriber(topic, "Lucia");
            consumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            throw new ReceiveException(e.getMessage());
        }
    }

    private void processMessage(Message message) {
        try {
            String text = ((TextMessage) message).getText();
            System.out.println("Message received: " + text);
            new EventWriter().insertEvent(text);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
