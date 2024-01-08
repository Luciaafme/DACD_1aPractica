package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.StoreException;
import practica1_dacd_afonso_medina.model.Location;
import practica1_dacd_afonso_medina.model.Weather;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.TimerTask;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherController extends TimerTask {
    private final OpenWeatherMapSupplier openWeatherMapSupplier;
    private final JmsWeatherStore jmsWeatherStore;
    private final List<Location> listLocation;

    public WeatherController(OpenWeatherMapSupplier openWeatherMapSupplier, JmsWeatherStore jmsWeatherStore, String filePath) {
        this.openWeatherMapSupplier = openWeatherMapSupplier;
        this.jmsWeatherStore = jmsWeatherStore;
        this.listLocation = readLocationsFromFile(filePath);
    }
    private static List<Location> readLocationsFromFile(String filePath) {
        List<Location> locations = new ArrayList<>();
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream != null) {
                try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
                    while (scanner.hasNextLine()) {
                        String[] columns = scanner.nextLine().split(" ");
                        if (columns.length >= 4) {
                            double latitude = Double.parseDouble(columns[0]);
                            double longitude = Double.parseDouble(columns[1]);
                            String name = columns[2];
                            String description = columns[3];
                            locations.add(new Location(latitude, longitude, name, description));
                        }
                    }
                }
            } else {
                System.err.println("Archive: " + filePath + " not found");
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error reading locations from file", e);
        }
        return locations;
    }

    public void execute() {
        try {
            for (Location location : listLocation) {
                List<Weather> weatherList = openWeatherMapSupplier.getWeather(location);
                jmsWeatherStore.save(weatherList);
            }
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        execute();
    }
}
