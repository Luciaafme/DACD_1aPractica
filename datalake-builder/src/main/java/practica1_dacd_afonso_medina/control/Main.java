package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.ReceiveException;


public class Main {
    public static void main(String[] args) throws ReceiveException {
        TopicSubscriber weatherSubscriber = new TopicSubscriber(new FileEventBuilder(args[0]), "prediction.Weather", "weather-provider");
        weatherSubscriber.start();
        TopicSubscriber accommodationSubscriber = new TopicSubscriber(new FileEventBuilder(args[0]), "prediction.Booking", "travel-provider");
        accommodationSubscriber.start();
    }
}