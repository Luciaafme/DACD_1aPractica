package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.ReceiveException;
import practica1_dacd_afonso_medina.control.exception.SqliteException;
import practica1_dacd_afonso_medina.view.BookingCalculator;
import practica1_dacd_afonso_medina.view.UserInterface;
import practica1_dacd_afonso_medina.view.WeatherCalculator;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws ReceiveException, SqliteException {
        MessageReceiver messageReceiver = new MessageReceiver("prediction.Booking", new DatamartManager(args[0], new EventModelBuilder()));
        MessageReceiver messageReceiver2 = new MessageReceiver("prediction.Weather", new DatamartManager(args[0], new EventModelBuilder()));
        messageReceiver2.start();
        messageReceiver.start();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new UserInterface(new WeatherCalculator(args[0]), new BookingCalculator(args[0]));
                } catch (SqliteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}