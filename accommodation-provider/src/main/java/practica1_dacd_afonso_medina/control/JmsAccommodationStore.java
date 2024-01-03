package practica1_dacd_afonso_medina.control;

import com.google.gson.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.StoreException;
import practica1_dacd_afonso_medina.model.Booking;
import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JmsAccommodationStore implements AccommodationStore{
    private final String brokerUrl = "tcp://localhost:61616";
    private final String topicName = "prediction.Booking";
    private Connection connection;
    private Session session;


    @Override
    public void save(List<Booking> bookingList) throws StoreException {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(destination);

            for (Booking booking : bookingList) {

                String bookingSerialize = serializeWeatherToJson(booking);
                TextMessage message = session.createTextMessage(bookingSerialize);
                producer.send(message);
                System.out.println(bookingSerialize);
            }
            connection.close();
        }
        catch (JMSException e){
            throw new StoreException(e.getMessage());
        }
    }
    public String serializeWeatherToJson(Booking weather) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new JmsAccommodationStore.InstantAdapter())
                .create();
        return gson.toJson(weather);
    }
    private static class InstantAdapter implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}
