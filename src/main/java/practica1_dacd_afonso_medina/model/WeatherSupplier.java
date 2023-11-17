package practica1_dacd_afonso_medina.model;

import java.util.List;

public interface WeatherSupplier {
    List<Weather> getWeather(Location location);
}
