package practica1_dacd_afonso_medina.model;

public class Location {
    private double latitud;
    private double longitud;
    private String island;

    public Location(double latitud, double longitud, String island) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.island = island;
    }
    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getIsland() {
        return island;
    }
}

