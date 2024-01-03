package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.StoreException;
import practica1_dacd_afonso_medina.model.Booking;

import java.util.List;

public interface AccommodationStore {
    void save(List<Booking> bookingList) throws StoreException;

}
