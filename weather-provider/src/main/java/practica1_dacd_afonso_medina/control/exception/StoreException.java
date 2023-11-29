package practica1_dacd_afonso_medina.control.exception;

import javax.jms.JMSException;

public class StoreException extends JMSException {

    public StoreException(String reason) {
        super(reason);
    }
}
