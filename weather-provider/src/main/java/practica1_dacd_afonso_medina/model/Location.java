package practica1_dacd_afonso_medina.model;

public class Location {
    private double latitude;
    private double longitude;
    private String island;
    private String zone;

    public Location(double latitude, double longitude, String island, String zone) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.island = island;
        this.zone = zone;
    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}