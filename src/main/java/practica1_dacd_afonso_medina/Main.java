package practica1_dacd_afonso_medina;

import practica1_dacd_afonso_medina.model.Location;

public class Main {
    public static void main(String[] args) {
        Location location = new Location(28.12380904158049,-15.436162953343267, "GranCanaria");
        FileManager fileManager = new FileManager();
        String apikey = fileManager.readFile("C:\\Users\\lucia\\IdeaProjects\\DACD_1aPractica\\src\\main\\resources\\apiKey.txt");
        OpenWeatherMapSupplier openWeatherMapSupplier = new OpenWeatherMapSupplier(apikey);
        openWeatherMapSupplier.getWeather(location);
    }
}