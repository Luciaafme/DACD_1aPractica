package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.StoreException;
import practica1_dacd_afonso_medina.model.Booking;
import java.util.List;
import java.util.TimerTask;

public class AccommodationController extends TimerTask {
    private XoteloApiSupplier xoteloApiSupplier;
    private JmsAccommodationStore jmsAccommodationStore;

    public AccommodationController(XoteloApiSupplier xoteloApiSupplier, JmsAccommodationStore jmsAccommodationStore) {
        this.xoteloApiSupplier = xoteloApiSupplier;
        this.jmsAccommodationStore = jmsAccommodationStore;
    }

    private void execute(){
        try {
            List<Booking> bookingList = xoteloApiSupplier.getBooking();
            for(Booking booking: bookingList) {
                System.out.println("PRINT OBJECT: "+ booking.toString());
            }
            jmsAccommodationStore.save(bookingList);
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        execute();
    }
}