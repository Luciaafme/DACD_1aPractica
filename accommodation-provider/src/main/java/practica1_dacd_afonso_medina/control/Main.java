package practica1_dacd_afonso_medina.control;
import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        long period = 6 * 60 * 60 * 1000;
        AccommodationController accommodationController = new AccommodationController(new XoteloApiSupplier(args[0]), new JmsAccommodationStore());
        timer.schedule(accommodationController, 0, period);
    }
}