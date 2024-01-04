package practica1_dacd_afonso_medina.model;
import java.time.Instant;

public class Hotel {
    private String hotelName;
    private String platform;
    private double price;
    private Instant checkIn;
    private Location location;

    public Hotel(String hotelName, String platform, double price, Instant checkIn, Location location) {
        this.hotelName = hotelName;
        this.platform = platform;
        this.price = price;
        this.checkIn = checkIn;
        this.location = location;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getPlatform() {
        return platform;
    }

    public double getPrice() {
        return price;
    }

    public Instant getCheckIn() {
        return checkIn;
    }

    public Location getLocation() {
        return location;
    }
}
