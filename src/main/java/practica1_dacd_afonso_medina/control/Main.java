package practica1_dacd_afonso_medina.control;
import java.sql.SQLException;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws SQLException {

        Timer timer = new Timer();
        long period = 6 * 60 * 60 * 1000;

        WeatherControler weatherControler = new WeatherControler(new OpenWeatherMapSupplier(args[0]),new SqLiteWeatherStore(args[1]));
        timer.schedule(weatherControler,0, period);

    }
}