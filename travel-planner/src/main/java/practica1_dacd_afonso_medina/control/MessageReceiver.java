package practica1_dacd_afonso_medina.control;

import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.ReceiveException;
import practica1_dacd_afonso_medina.control.exception.SqliteException;

import javax.jms.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageReceiver {
    private static String brokerUrl = "tcp://localhost:61616";
    private String topicName;
    private Connection connection;
    private Session session;
    private DatamartManager datamartManager;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public MessageReceiver(String topicName, DatamartManager datamartManager) {
        this.topicName = topicName;
        this.datamartManager = datamartManager;
    }

    public void start() throws ReceiveException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(session.createTopic(topicName));

            // Inicia el temporizador para el borrado cada 6 horas
            startScheduledTask();

            consumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            throw new ReceiveException(e.getMessage(), e);
        }
    }

    private void processMessage(Message message) {
        try {
            String text = ((TextMessage) message).getText();
            System.out.println("Message received: " + text);

            if (topicName.substring(11).equals("Weather")) {
                datamartManager.insertWeather(text);
            } else {
                datamartManager.insertHotel(text);
            }

        } catch (JMSException | SqliteException e) {
            throw new RuntimeException(e);
        }
    }

    private void startScheduledTask() {
        scheduler.scheduleAtFixedRate(this::deleteTable, 0, 6, TimeUnit.HOURS);

    }

    private void deleteTable() {
        datamartManager.delete(topicName);
        System.out.println("Table deleted successfully.");
    }
}
