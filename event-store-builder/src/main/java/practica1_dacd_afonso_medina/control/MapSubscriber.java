package practica1_dacd_afonso_medina.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.ReceiveException;
import javax.jms.*;

public class MapSubscriber implements Subscriber{
    private static final String topicName = "prediction.Weather";
    private static final String brokerUrl =  "tcp://localhost:61616";
    private  FileEventBuilder fileEventBuilder;

    public MapSubscriber(FileEventBuilder fileEventBuilder) {
        this.fileEventBuilder = fileEventBuilder;
    }

    @Override
    public void start() throws ReceiveException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("weather-provider");
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createDurableSubscriber(session.createTopic(topicName), "weather-provider_" + topicName);
            consumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            throw new ReceiveException(e.getMessage());
        }
    }
    private void processMessage(Message message){
        try {
            String text = ((TextMessage) message).getText();
            System.out.println("Message received: " + text);
            fileEventBuilder.save(text);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
