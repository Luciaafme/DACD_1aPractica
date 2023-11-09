package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.FileManager;
import practica1_dacd_afonso_medina.OpenWeatherMapSupplier;
import practica1_dacd_afonso_medina.SqLiteWeatherStore;
import practica1_dacd_afonso_medina.WeatherControler;
import practica1_dacd_afonso_medina.model.Location;

import java.sql.SQLException;
import java.util.List;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws SQLException {

        FileManager fileManager = new FileManager();
        String apikey = fileManager.readFile("C:\\Users\\lucia\\IdeaProjects\\DACD_1aPractica\\src\\main\\resources\\apiKey.txt");

        Timer timer = new Timer();
        //long period = 6 * 60 * 60 * 1000; // 6 horas en milisegundos
        long period =  5 * 60 * 1000; // 6 horas en milisegundos

        String path = "C:\\Users\\lucia\\IdeaProjects\\DACD_1aPractica\\src\\main\\resources\\path.txt";
        WeatherControler weatherControler = new WeatherControler(new OpenWeatherMapSupplier(apikey),new SqLiteWeatherStore(path));
        timer.schedule(weatherControler,0, period);

    }
}