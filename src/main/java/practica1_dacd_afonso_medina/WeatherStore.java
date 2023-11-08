package practica1_dacd_afonso_medina;

import practica1_dacd_afonso_medina.model.Weather;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface WeatherStore {
    void save(List<Weather> weatherPrediction) throws SQLException;

}
