package practica1_dacd_afonso_medina;

import practica1_dacd_afonso_medina.model.Location;
import practica1_dacd_afonso_medina.model.Weather;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.TimerTask;


public class WeatherControler extends TimerTask {
    private OpenWeatherMapSupplier openWeatherMapSupplier;
    private SqLiteWeatherStore sqLiteWeatherStore;
    List<Location> listLocation = List.of(
            new Location(28.12380904158049, -15.436162953343267, "GranCanaria"),
            new Location(28.463850790803008, -16.25097353346818, "Tenerife"),
            new Location(28.50047229032077, -13.863339828212446, "Fuerteventura"),
            new Location(28.965080860301025, -13.556148106209083, "Lanzarote"),
            new Location(29.23141101200906, -13.503131221117982, "LaGraciosa"),
            new Location(27.809920552606453, -17.91474223115781, "ElHierro"),
            new Location(28.094369991798228, -17.109467831251514, "LaGomera"),
            new Location(28.684160726614596, -17.76582062032028, "LaPalma"));

    public WeatherControler(OpenWeatherMapSupplier openWeatherMapSupplier, SqLiteWeatherStore sqLiteWeatherStore) {
        this.openWeatherMapSupplier = openWeatherMapSupplier;
        this.sqLiteWeatherStore = sqLiteWeatherStore;
    }

    public void execute() throws SQLException, ParseException {
        for (Location location : listLocation) {
            List<Weather> weatherList = openWeatherMapSupplier.getWeather(location);
            sqLiteWeatherStore.save(weatherList);
        }
    }
    @Override
    public void run() {
        try {
            execute();
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
