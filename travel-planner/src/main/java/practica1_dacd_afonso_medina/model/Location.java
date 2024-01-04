package practica1_dacd_afonso_medina.model;

public class Location {
    private String zone;
    private String island;

    public Location(String zone, String island) {
        this.zone = zone;
        this.island = island;
    }

    public String getZone() {
        return zone;
    }

    public String getIsland() {
        return island;
    }
}