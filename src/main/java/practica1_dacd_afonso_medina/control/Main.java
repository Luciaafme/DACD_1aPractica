package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.FileManager;
import practica1_dacd_afonso_medina.OpenWeatherMapSupplier;
import practica1_dacd_afonso_medina.SqLiteWeatherStore;
import practica1_dacd_afonso_medina.model.Location;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<Location> listLocation = List.of(
                new Location(28.12380904158049, -15.436162953343267, "GranCanaria"),
                new Location(28.463850790803008, -16.25097353346818, "Tenerife"),
                new Location(28.50047229032077, -13.863339828212446, "Fuerteventura"),
                new Location(28.965080860301025, -13.556148106209083, "Lanzarote"),
                new Location(29.23141101200906, -13.503131221117982, "LaGraciosa"),
                new Location(27.809920552606453, -17.91474223115781, "ElHierro"),
                new Location(28.094369991798228, -17.109467831251514, "LaGomera"),
                new Location(28.684160726614596, -17.76582062032028, "LaPalma"));
        FileManager fileManager = new FileManager();
        String apikey = fileManager.readFile("C:\\Users\\lucia\\IdeaProjects\\DACD_1aPractica\\src\\main\\resources\\apiKey.txt");


        for(Location location:listLocation) {
            OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier(apikey);
            SqLiteWeatherStore sqLiteWeatherStore = new SqLiteWeatherStore("C:\\Users\\lucia\\IdeaProjects\\DACD_1aPractica\\src\\main\\resources\\path.txt");
            sqLiteWeatherStore.save(openWeatherMapSupplier.getWeather(location));
        }
    }
}