package practica1_dacd_afonso_medina.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.ReceiveException;
import practica1_dacd_afonso_medina.control.exception.SqliteException;

import javax.jms.*;

public class MessageReceiver{
    private static String brokerUrl = "tcp://localhost:61616";
    private String topicName;
    private Connection connection;
    private Session session;
    private Datamart datamart;

    public MessageReceiver(String topicName, Datamart datamart) {
        this.topicName = topicName;
        this.datamart = datamart;
    }
    public void start() throws ReceiveException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(session.createTopic(topicName));
            datamart.delete(topicName);
            consumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            throw new ReceiveException(e.getMessage(), e);
        }
    }

    private void processMessage(Message message) {
        try {
            String text = ((TextMessage) message).getText();
            System.out.println("Message received: " + text);

            if(topicName.substring(11).equals("Weather")){
                datamart.insertWeather(text);
            }
            else {
                datamart.insertHotel(text);
            }

        } catch (JMSException | SqliteException e) {
            throw new RuntimeException(e);
        }
    }
}
