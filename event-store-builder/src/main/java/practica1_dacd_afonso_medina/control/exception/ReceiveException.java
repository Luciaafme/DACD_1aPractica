package practica1_dacd_afonso_medina.control.exception;

import javax.jms.JMSException;

public class ReceiveException extends JMSException {
    public ReceiveException(String reason) {
        super(reason);
    }
}