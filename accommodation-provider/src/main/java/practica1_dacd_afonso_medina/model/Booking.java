package practica1_dacd_afonso_medina.model;

import java.time.Instant;

public class Booking {

    private Instant ts;
    private String ss;
    private Hotel hotel;
    private String checkIn;
    private String checkOut;
    private String platform;
    private Double price;

    public Booking(Hotel hotel, String checkIn, String checkOut, String platform, Double price) {
        this.ts = Instant.now();
        this.ss = "accommodation-provider";
        this.hotel = hotel;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.platform = platform;
        this.price = price;
    }


    public Hotel getHotel() {
        return hotel;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getPlatform() {
        return platform;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "ts=" + ts +
                ", ss='" + ss + '\'' +
                ", hotel=" + hotel.getName() +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                ", platform='" + platform + '\'' +
                ", price=" + price +
                '}';
    }
}
