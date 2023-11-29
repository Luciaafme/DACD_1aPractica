package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.StoreException;
import practica1_dacd_afonso_medina.model.Weather;
import java.util.List;

public interface WeatherStore {
    void save(List<Weather> weatherPrediction)  throws StoreException;

}
