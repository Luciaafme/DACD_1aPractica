package practica1_dacd_afonso_medina.model;

public class Weather {
    private String ts;
    private int humidity;
    private double windSpeed;
    private double temperature;
    private int clouds;
    private double precipitation;
    private Location location;

    public Weather(String ts, int humidity, double windSpeed, double temperature, int clouds, double precipitation, Location location) {
        this.ts = ts;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.clouds = clouds;
        this.precipitation = precipitation;
        this.location = location;
    }

    public String getTs() {
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

}
