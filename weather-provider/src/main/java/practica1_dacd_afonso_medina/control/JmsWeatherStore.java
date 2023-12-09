package practica1_dacd_afonso_medina.control;

import com.google.gson.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import practica1_dacd_afonso_medina.control.exception.StoreException;
import practica1_dacd_afonso_medina.model.Weather;
import javax.jms.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;

public class JmsWeatherStore implements WeatherStore{
    private final String brokerUrl = "tcp://localhost:61616";
    private final String topicName = "prediction.Weather";

    @Override
    public void save(List<Weather> weatherPrediction) throws StoreException{
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(destination);
            for (Weather weather : weatherPrediction) {
                String weatherSerialize = serializeWeatherToJson(weather);
                TextMessage message = session.createTextMessage(weatherSerialize);
                producer.send(message);
                System.out.println(weatherSerialize);
            }
            connection.close();
        }
        catch (JMSException e){
            throw new StoreException(e.getMessage());
        }
    }

    public String serializeWeatherToJson(Weather weather) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new JmsWeatherStore.InstantAdapter())
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
