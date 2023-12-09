package practica1_dacd_afonso_medina.control;
import java.util.Timer;
public class Main {
    public static void main(String[] args){

        Timer timer = new Timer();
        long period = 6 * 60 * 60 * 1000;
        WeatherController weatherController = new WeatherController(new OpenWeatherMapSupplier(args[0]),new JmsWeatherStore());
        timer.schedule(weatherController,0, period);

    }
}