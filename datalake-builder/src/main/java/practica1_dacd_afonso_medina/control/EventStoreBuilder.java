package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.FileEventBuilderException;

public interface EventStoreBuilder {
    void save(String message, String topic) throws FileEventBuilderException;
}
