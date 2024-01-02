package practica1_dacd_afonso_medina.model;

public class Hotel {
    private String island;
    private String name;
    private String id;
    private String zone;

    public Hotel(String island, String name, String id, String zone) {
        this.island = island;
        this.name = name;
        this.id = id;
        this.zone = zone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIsland() {
        return island;
    }

    public String getZone() {
        return zone;
    }
}