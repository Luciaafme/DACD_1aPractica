package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.ReceiveException;

public interface Subscriber {
    void start() throws ReceiveException;
}
