package practica1_dacd_afonso_medina.model;

import java.sql.SQLException;
import java.util.List;

public interface WeatherStore {
    void save(List<Weather> weatherPrediction) throws SQLException;

}
