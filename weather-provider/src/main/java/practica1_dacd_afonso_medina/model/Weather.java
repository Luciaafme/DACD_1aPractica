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
    public String getSs() {
        return ss;
    }
    public Instant getPredictionTime() {
        return predictionTime;
    }
    public Instant getTs() {
        return ts;
    }
    public int getHumidity() {
        return humidity;
    }
    public double getWindSpeed() {
        return windSpeed;
    }
    public double getTemperature() {
        return temperature;
    }
    public int getClouds() {
        return clouds;
    }
    public double getPrecipitation() {
        return precipitation;
    }
    public Location getLocation() {
        return location;
    }
    @Override
    public String toString() {
        return "Weather{" +
                "ts=" + ts +
                ", ss='" + ss + '\'' +
                ", predictionTime=" + predictionTime +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", temperature=" + temperature +
                ", clouds=" + clouds +
                ", precipitation=" + precipitation +
                ", location=" + location +
                '}';
    }
}

