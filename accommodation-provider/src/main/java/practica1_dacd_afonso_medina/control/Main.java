package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.StoreException;
import practica1_dacd_afonso_medina.model.Booking;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        XoteloApiSupplier xoteloApiSupplier = new XoteloApiSupplier();
        JmsAccommodationStore jmsAccommodationStore = new JmsAccommodationStore();
        List<Booking> bookingList = xoteloApiSupplier.getBooking();
        for(Booking booking: bookingList) {
            System.out.println("PRINT OBJECT: "+ booking.toString());
        }
        try {
            jmsAccommodationStore.save(bookingList);
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
    }
}