package practica1_dacd_afonso_medina.control;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import practica1_dacd_afonso_medina.control.exception.OpenWeatherApiException;
import practica1_dacd_afonso_medina.model.Location;
import practica1_dacd_afonso_medina.model.Weather;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class OpenWeatherMapSupplier implements WeatherSupplier {

    private final String apikey;

    public OpenWeatherMapSupplier(String apikey) {

        this.apikey = apikey;
    }

    public String getApiUrl(Location location) {
        return "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + apikey + "&units=metric";
    }

    public JsonObject getJsonData(String apiUrl) throws OpenWeatherApiException {
        try {
            Document result = Jsoup.connect(apiUrl).ignoreContentType(true).get();
            JsonParser parser = new JsonParser();
            return parser.parse(result.text()).getAsJsonObject();
        } catch (IOException e) {
            throw new OpenWeatherApiException("Error getting JSON data from OpenWeatherMap API", e);
        }
    }

    private Instant dateFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    private Weather createWeatherObject(JsonObject listItem, Location location) {
        String date = listItem.get("dt_txt").getAsString();
        Instant predictionTime = dateFormatter(date);
        int humidity = listItem.getAsJsonObject("main").get("humidity").getAsInt();
        double temp = listItem.getAsJsonObject("main").get("temp").getAsDouble();
        double precipitation = listItem.get("pop").getAsDouble();
        int clouds = listItem.getAsJsonObject("clouds").get("all").getAsInt();
        double windSpeed = listItem.getAsJsonObject("wind").get("speed").getAsDouble();
        return new Weather(predictionTime, humidity, windSpeed, temp, clouds, precipitation, location);
    }

    @Override
    public List<Weather> getWeather(Location location) {
        String apiUrl = getApiUrl(location);
        List<Weather> weatherList = new ArrayList<>();
        try {
            JsonObject jsonResult = getJsonData(apiUrl);
            JsonArray list = jsonResult.getAsJsonArray("list");
            for (int i = 0; i < list.size(); i++) {
                JsonObject listItem = list.get(i).getAsJsonObject();
                String hour = String.valueOf(listItem.get("dt_txt")).substring(12, 20);
                if (hour.equals("12:00:00")) {
                    Weather weather = createWeatherObject(listItem, location);
                    weatherList.add(weather);
                }
            }
        } catch (OpenWeatherApiException e) {
            e.printStackTrace();
        }
        return weatherList;
    }
}