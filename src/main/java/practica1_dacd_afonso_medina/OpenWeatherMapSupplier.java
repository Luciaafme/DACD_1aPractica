package practica1_dacd_afonso_medina;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import practica1_dacd_afonso_medina.model.Location;
import practica1_dacd_afonso_medina.model.Weather;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OpenWeatherMapSupplier implements WeatherSupplier {

    private final String apikey;

    public OpenWeatherMapSupplier(String apikey) {

        this.apikey = apikey;
    }

    @Override
    public List<Weather> getWeather(Location location) {

        String apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=" + location.getLatitud() + "&lon=" + location.getLongitud() + "&appid=" + apikey;

        Weather weather = null;
        List<Weather> weatherPrediction = new ArrayList<>();

        try {
            Document document = Jsoup.connect(apiUrl).ignoreContentType(true).get();
            String jsonData = document.text();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonData).getAsJsonObject();
            JsonArray list = jsonObject.getAsJsonArray("list");
            for (int i = 0; i < list.size(); i++) {
                JsonObject listItem = list.get(i).getAsJsonObject();
                String hour = String.valueOf(listItem.get("dt_txt")).substring(12,20);
                System.out.println(hour);
                String date = String.valueOf(listItem.get("dt_txt"));

                if (hour.equals("00:00:00")) {
                    int humidity = listItem.getAsJsonObject("main").get("humidity").getAsInt();
                    double temp = listItem.getAsJsonObject("main").get("temp").getAsDouble();
                    double precipitation = listItem.get("pop").getAsDouble();
                    int clouds = listItem.getAsJsonObject("clouds").get("all").getAsInt();
                    double windSpeed = listItem.getAsJsonObject("wind").get("speed").getAsDouble();
                    weather = new Weather(date, humidity, windSpeed, temp, clouds, precipitation, location);
                    weatherPrediction.add(weather);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(weatherPrediction);
        return weatherPrediction;
    }
}