package practica1_dacd_afonso_medina.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.FileEventBuilderException;
import practica1_dacd_afonso_medina.control.exception.ReceiveException;
import javax.jms.*;

public class TopicSubscriber implements Subscriber{
    private final String topicName = "prediction.Weather";
    private final String brokerUrl =  "tcp://localhost:61616";
    private final FileEventBuilder fileEventBuilder;
    private Connection connection;
    private Session session;

    public TopicSubscriber(FileEventBuilder fileEventBuilder) {
        this.fileEventBuilder = fileEventBuilder;
    }

    @Override
    public void start() throws ReceiveException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            connection = connectionFactory.createConnection();
            connection.setClientID("weather-provider");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createDurableSubscriber(session.createTopic(topicName), "weather-provider_" + topicName);
            consumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            throw new ReceiveException(e.getMessage(), e);
        }
    }
    private void processMessage(Message message){
        try {
            String text = ((TextMessage) message).getText();
            System.out.println("Message received: " + text);
            fileEventBuilder.save(text);
        } catch (JMSException | FileEventBuilderException e) {
            throw new RuntimeException();
        }
    }
}
