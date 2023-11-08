package practica1_dacd_afonso_medina;

import practica1_dacd_afonso_medina.model.Weather;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SqLiteWeatherStore implements WeatherStore {
    private String file;
    private Connection connection;
    private Statement statement;

    public SqLiteWeatherStore(String file) throws SQLException {
        FileManager fileManager = new FileManager();
        String pathDB = fileManager.readFile(file);
        this.file = pathDB;
        this.connection = connect(pathDB);
        this.statement = connection.createStatement();
    }

    private void createTableIsland(Weather weather) throws SQLException {
        //for(String island: islands) {
            statement.execute("CREATE TABLE IF NOT EXISTS weather_" + weather.getLocation().getIsland() + "(" +
                    "Date TEXT,\n" +
                    "WindSpeed REAL,\n" +
                    "Precipitation REAL,\n" +
                    "Clouds INTEGER,\n" +
                    "Temperature REAL,\n" +
                    "Humidity INTEGER" +
                    ");");
        //}
    }

    public static Connection connect(String dbPath) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(Weather weather) throws SQLException {
        String strsql_insert = null;
        String tableIsland = "weather_" + weather.getLocation().getIsland();
        strsql_insert = "INSERT INTO " + tableIsland + "(Date, WindSpeed, Precipitation, Clouds, Temperature, Humidity)" +
                " SELECT " + weather.getTs() + ", " + weather.getWindSpeed() + ", " + weather.getPrecipitation() + ", " + weather.getClouds() + ", " + weather.getTemperature() + ", " + weather.getHumidity() +
                " WHERE NOT EXISTS (SELECT 1 FROM " + tableIsland + " WHERE Date = " + weather.getTs() + ")";
        System.out.println(strsql_insert);
        statement.execute(strsql_insert);

    }

    private void update(Weather weather) throws SQLException {
        String tableIsland = "weather_" + weather.getLocation().getIsland();
        String query = "UPDATE " + tableIsland +
                " SET Date = " + weather.getTs() +
                ", WindSpeed = " + weather.getWindSpeed() +
                ", Precipitation = " + weather.getPrecipitation() +
                ", Clouds = " + weather.getClouds() +
                ", Temperature = " + weather.getTemperature() +
                ", Humidity = " + weather.getHumidity()+
                " WHERE Date = " + weather.getTs();
        statement.executeUpdate(query);
        System.out.println("Updated");
    }

    @Override
    public void save(List<Weather> weatherPrediction) throws SQLException {
        for(Weather weather:weatherPrediction){
            createTableIsland(weather);
            update(weather);
            insert(weather);
        }
    }
}