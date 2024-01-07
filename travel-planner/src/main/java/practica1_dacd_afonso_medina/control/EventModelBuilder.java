package practica1_dacd_afonso_medina.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import practica1_dacd_afonso_medina.model.Hotel;
import practica1_dacd_afonso_medina.model.Location;
import practica1_dacd_afonso_medina.model.Weather;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EventModelBuilder {
    public Weather weatherBuilder(String jsonData) {
        JsonObject responseJson = new Gson().fromJson(jsonData, JsonObject.class);
        Instant instant = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(responseJson.get("predictionTime").getAsString()));
        Location location = getLocation(responseJson.getAsJsonObject("location"));
        return new Weather(instant, location, responseJson.get("temperature").getAsDouble(),
                responseJson.get("clouds").getAsInt(), responseJson.get("precipitation").getAsDouble());
    }

    public Hotel hotelBuilder(String jsonData) {
        JsonObject jsonObject = new Gson().fromJson(jsonData, JsonObject.class);
        JsonObject hotelObject = jsonObject.getAsJsonObject("hotel");
        Instant instant = LocalDate.parse(jsonObject.get("checkIn").getAsString()).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Location location = getLocation(hotelObject);
        return new Hotel(hotelObject.get("name").getAsString(), jsonObject.get("platform").getAsString(),
                jsonObject.get("price").getAsDouble(), instant, location);
    }

    private Location getLocation(JsonObject locationObject) {
        return new Location(locationObject.get("zone").getAsString(), locationObject.get("island").getAsString());
    }
}
