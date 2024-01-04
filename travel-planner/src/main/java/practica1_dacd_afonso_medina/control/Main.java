package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.ReceiveException;
import practica1_dacd_afonso_medina.control.exception.SqliteException;

public class Main {
    public static void main(String[] args) throws ReceiveException, SqliteException {
        MessageReceiver messageReceiver = new MessageReceiver("prediction.Booking", new Datamart(args[0], new EventStoreBuilder()));
        MessageReceiver messageReceiver2 = new MessageReceiver("prediction.Weather", new Datamart(args[0], new EventStoreBuilder()));
        messageReceiver2.start();
        messageReceiver.start();
    }
}