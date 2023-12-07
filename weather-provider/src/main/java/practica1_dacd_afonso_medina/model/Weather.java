package practica1_dacd_afonso_medina.model;

import java.time.Instant;

public class Weather {

    private Instant ts;
    private String ss;
    private Instant predictionTime;
    private int humidity;
    private double windSpeed;
    private double temperature;
    private int clouds;
    private double precipitation;
    private Location location;

    public Weather(Instant predictionTime, int humidity, double windSpeed, double temperature, int clouds, double precipitation, Location location) {
        this.ts = Instant.now();
        this.ss = "weather-provider";
        this.predictionTime = predictionTime;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.clouds = clouds;
        this.precipitation = precipitation;
        this.location = location;
    }
}

