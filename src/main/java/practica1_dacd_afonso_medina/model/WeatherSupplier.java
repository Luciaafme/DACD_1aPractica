package practica1_dacd_afonso_medina.model;

import practica1_dacd_afonso_medina.model.Location;
import practica1_dacd_afonso_medina.model.Weather;

import java.util.List;

public interface WeatherSupplier {
    List<Weather> getWeather(Location location);
}
