package practica1_dacd_afonso_medina.control;
import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import practica1_dacd_afonso_medina.control.exception.XoteloApiException;
import practica1_dacd_afonso_medina.model.Booking;
import practica1_dacd_afonso_medina.model.Hotel;
import java.io.*;
import java.time.*;
import java.util.*;

public class XoteloApiSupplier implements AccommodationSupplier {

    private static final String HOTEL_INFO_FILE_PATH = "accommodation-provider\\src\\main\\resources\\idHotel.tsv";

    private static List<Hotel> readHotelInfoFromFile() {
        List<Hotel> hotelList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(HOTEL_INFO_FILE_PATH))) {
            while (scanner.hasNextLine()) {
                String[] columns = scanner.nextLine().split(" ");
                if (columns.length >= 3) hotelList.add(new Hotel(columns[0], columns[1], columns[2], columns[3]));
            }
        } catch (FileNotFoundException e) { throw new RuntimeException(e); }
        return hotelList;
    }
    private static List<Booking> fetchPricesForDates(OkHttpClient client, Hotel hotel) throws XoteloApiException {
        List<Booking> listHotels = new ArrayList<>();
        LocalTime currentTime = LocalTime.now();
        int startDay = currentTime.isAfter(LocalTime.parse("12:00:00")) ? 1 : 0;
        for (int i = startDay; i < 5; i++) {
            String checkOut = getFormattedDate(i + 1), checkIn = getFormattedDate(i);
            String apiUrl = "https://data.xotelo.com/api/rates?hotel_key=" + hotel.getId() + "&chk_in=" + checkIn + "&chk_out=" + checkOut + "&currency=EUR";
            try {
                Response xoteloResponse = client.newCall(new Request.Builder().url(apiUrl).get().build()).execute();
                listHotels.addAll(createHotelObjects(xoteloResponse.body().string(), hotel, checkIn, checkOut));
            } catch (IOException e) {
                throw new XoteloApiException("Error fetching prices from Xotelo API", e);
            }
        }
        return listHotels;
    }

    private static List<Booking> createHotelObjects(String xoteloData, Hotel hotel, String checkIn, String checkOut) {
        List<Booking> listHotels = new ArrayList<>();
        JsonObject responseJson = new Gson().fromJson(xoteloData, JsonObject.class);
        if (responseJson.has("result")) {
            JsonArray dataArray = responseJson.getAsJsonObject("result").getAsJsonArray("rates");
            for (int j = 0; j < dataArray.size(); j++) {
                JsonObject rateObject = dataArray.get(j).getAsJsonObject();
                String name = rateObject.get("name").getAsString();
                int rate = rateObject.get("rate").getAsInt(), tax = rateObject.get("tax").getAsInt(), totalPrice = rate + tax;
                listHotels.add(new Booking(new Hotel(hotel.getIsland(), hotel.getName(), hotel.getId(), hotel.getZone()), checkIn, checkOut, name, (double) totalPrice));
            }
            if (dataArray.isEmpty()) listHotels.add(new Booking(new Hotel(hotel.getIsland(), hotel.getName(), hotel.getId(), hotel.getZone()), checkIn, checkOut, "No availability", 0.0));
        } else System.out.println("The 'result' property was not found in the JSON.");
        return listHotels;
    }
    @Override
    public List<Booking> getBooking() {
        List<Hotel> hotelList = readHotelInfoFromFile();
        List<Booking> listHotels = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        for (Hotel hotel : hotelList) {
            try {
                listHotels.addAll(fetchPricesForDates(client, hotel));
            } catch (XoteloApiException e) {
                e.printStackTrace();
            }
        }
        return listHotels;
    }
    private static String getFormattedDate(int daysToAdd) {
        return String.valueOf(LocalDate.ofInstant(Instant.now().plusSeconds(daysToAdd * 24 * 60 * 60), ZoneId.systemDefault()));
    }
}