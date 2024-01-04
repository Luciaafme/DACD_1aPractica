package practica1_dacd_afonso_medina.model;
import java.time.Instant;

public class Weather {
    private Instant predictionTime;
    private Location location;
    private double temperature;
    private float clouds;
    private double precipitation;

    public Weather(Instant predictionTime, Location location, double temperature, float clouds, double precipitation) {
        this.predictionTime = predictionTime;
        this.location = location;
        this.temperature = temperature;
        this.clouds = clouds;
        this.precipitation = precipitation;
    }

    public Instant getPredictionTime() {
        return predictionTime;
    }

    public Location getLocation() {
        return location;
    }

    public double getTemperature() {
        return temperature;
    }

    public float getClouds() {
        return clouds;
    }

    public double getPrecipitation() {
        return precipitation;
    }
}