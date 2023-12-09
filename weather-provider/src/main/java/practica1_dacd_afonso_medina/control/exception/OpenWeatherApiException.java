package practica1_dacd_afonso_medina.control.exception;

import java.io.IOException;

public class OpenWeatherApiException extends IOException {
    public OpenWeatherApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
