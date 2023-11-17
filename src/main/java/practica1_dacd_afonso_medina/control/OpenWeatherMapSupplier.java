package practica1_dacd_afonso_medina.control;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import practica1_dacd_afonso_medina.model.Location;
import practica1_dacd_afonso_medina.model.Weather;
import practica1_dacd_afonso_medina.model.WeatherSupplier;

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

    public String getApiUrl(Location location){
        return  "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLatitude() + "&lon=" + location.getLongitude() + "&appid=" + apikey;
    }

    public JsonObject getJsonData(String apiUrl) throws IOException {
        Document result = Jsoup.connect(apiUrl).ignoreContentType(true).get();
        JsonParser parser = new JsonParser();
        return parser.parse(result.text()).getAsJsonObject();
    }

    @Override
    public List<Weather> getWeather(Location location) {

        Weather weather = null;
        String apiUrl = getApiUrl(location);
        List<Weather> weatherList = new ArrayList<>();

        try {
            JsonObject jsonResult = getJsonData(apiUrl);
            JsonArray list = jsonResult.getAsJsonArray("list");
            for (int i = 0; i < list.size(); i++) {
                JsonObject listItem = list.get(i).getAsJsonObject();
                String hour = String.valueOf(listItem.get("dt_txt")).substring(12,20);
                String date = listItem.get("dt_txt").getAsString();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
                Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

                if (hour.equals("12:00:00")) {
                    int humidity = listItem.getAsJsonObject("main").get("humidity").getAsInt();
                    double temp = listItem.getAsJsonObject("main").get("temp").getAsDouble();
                    double precipitation = listItem.get("pop").getAsDouble();
                    int clouds = listItem.getAsJsonObject("clouds").get("all").getAsInt();
                    double windSpeed = listItem.getAsJsonObject("wind").get("speed").getAsDouble();
                    weather = new Weather(instant, humidity, windSpeed, temp, clouds, precipitation, location);
                    weatherList.add(weather);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return weatherList;
    }
}