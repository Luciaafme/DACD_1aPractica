package practica1_dacd_afonso_medina.control;

import java.sql.SQLException;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws SQLException {
        String apikey = args[0];

        Timer timer = new Timer();
        long period = 6 * 60 * 60 * 1000;

        String path = "C:\\Users\\lucia\\IdeaProjects\\DACD_1aPractica\\src\\main\\resources\\path.txt";
        WeatherControler weatherControler = new WeatherControler(new OpenWeatherMapSupplier(apikey),new SqLiteWeatherStore(args[1]));
        timer.schedule(weatherControler,0, period);

    }
}