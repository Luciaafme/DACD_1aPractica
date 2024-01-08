package practica1_dacd_afonso_medina.control;

import practica1_dacd_afonso_medina.control.exception.SqliteException;
import practica1_dacd_afonso_medina.model.Hotel;
import practica1_dacd_afonso_medina.model.Weather;
import java.io.File;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DatamartManager {
    private final String dbpath;
    private DmConnection dmConnection;
    private Connection connection;
    private Statement statement;
    private EventModelBuilder eventStoreBuilder;

    public DatamartManager(String dbpath, EventModelBuilder eventStoreBuilder) throws SqliteException {
        this.dbpath = dbpath;
        this.eventStoreBuilder = eventStoreBuilder;
        try {
            new File(this.dbpath).getParentFile().mkdirs();
            this.connection = dmConnection.connect(dbpath);
            this.statement = connection.createStatement();
            createTableWeather();
            createTableHotel();
        } catch (SQLException e) {
            throw new SqliteException("Error initializing SQLite database.", e);
        }
    }

    public void createTableWeather() throws SqliteException {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS prediction_Weather ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "predictionTime TEXT,"
                    + "zone TEXT,"
                    + "island TEXT,"
                    + "temperature REAL,"
                    + "clouds REAL,"
                    + "precipitation REAL"
                    + ")");
        } catch (SQLException e) {
            throw new SqliteException("Error creating 'prediction_Weather' table.", e);
        }
    }

    public void createTableHotel() throws SqliteException {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS prediction_Booking ("
                    + "check_in TEXT,"
                    + "zone TEXT,"
                    + "island TEXT,"
                    + "hotel_name TEXT,"
                    + "platform TEXT,"
                    + "price REAL,"
                    + "UNIQUE(check_in, hotel_name)"
                    + ")");
        } catch (SQLException e) {
            throw new SqliteException("Error creating 'prediction_Booking' table.", e);
        }
    }

    public String dateFormatter(Instant date){
        LocalDateTime localDateTime = date.atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDateTime.format(formatter);
    }

    public void insertWeather(String jsonData) throws SqliteException {
        Weather weather = eventStoreBuilder.weatherBuilder(jsonData);
        String insertQuery = "INSERT INTO prediction_Weather (predictionTime, zone, island, temperature, clouds, precipitation) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, dateFormatter(weather.getPredictionTs()));
            preparedStatement.setString(2, weather.getLocation().getZone());
            preparedStatement.setString(3, weather.getLocation().getIsland());
            preparedStatement.setDouble(4, weather.getTemperature());
            preparedStatement.setFloat(5, weather.getClouds());
            preparedStatement.setDouble(6, weather.getPrecipitation());
            preparedStatement.executeUpdate();
            System.out.println("Data successfully inserted into prediction_Weather table");
        } catch (SQLException e) {
            throw new SqliteException("Error inserting data into rediction_Weather table", e);
        }
    }

    public void insertHotel(String jsonData) throws SqliteException{
        Hotel hotel = eventStoreBuilder.hotelBuilder(jsonData);
        if(hotel.getPrice() != 0.0) {
            String insertOrUpdateQuery = "INSERT INTO prediction_Booking (check_in, zone, island, hotel_name, platform, price)" +
                    "VALUES (?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT(check_in, hotel_name)" +
                    "DO UPDATE SET" +
                    "    check_in = excluded.check_in," +
                    "    zone = excluded.zone," +
                    "    island = excluded.island," +
                    "    platform = excluded.platform," +
                    "    price = CASE WHEN excluded.price < prediction_Booking.price THEN excluded.price ELSE prediction_Booking.price END " +
                    "WHERE prediction_Booking.price > excluded.price";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrUpdateQuery)) {
                preparedStatement.setString(1, dateFormatter(hotel.getCheckIn()));
                preparedStatement.setString(2, hotel.getLocation().getZone());
                preparedStatement.setString(3, hotel.getLocation().getIsland());
                preparedStatement.setString(4, hotel.getHotelName());
                preparedStatement.setString(5, hotel.getPlatform());
                preparedStatement.setDouble(6, hotel.getPrice());
                preparedStatement.executeUpdate();
                System.out.println("Data successfully inserted into prediction_Booking table");
            } catch (SQLException e) {
                throw new SqliteException("Error inserting data into prediction_Booking table", e);
            }
        }
    }

    public void delete(String topicName){
        try {
            if(topicName.substring(11).equals("Weather")){
                String deleteQuery = "DELETE FROM prediction_Weather";
                statement.executeUpdate(deleteQuery);
            }
            else {
                String deleteQuery = "DELETE FROM prediction_Booking";
                statement.executeUpdate(deleteQuery);
            }
            System.out.println("Delete operation completed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
