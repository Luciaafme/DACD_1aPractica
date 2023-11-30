package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.ReceiveException;


public class Main {
    public static void main(String[] args) throws ReceiveException {
        EventProcessor eventProcessor = new EventProcessor();
        eventProcessor.eventReceiver();
    }
}