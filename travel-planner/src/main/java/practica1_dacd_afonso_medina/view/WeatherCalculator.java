package practica1_dacd_afonso_medina.view;

import practica1_dacd_afonso_medina.control.DmConnection;
import practica1_dacd_afonso_medina.control.exception.SqliteException;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class WeatherCalculator {
    private Connection connection;
    private DmConnection dmConnection;
    private String dbpath;

    public WeatherCalculator(String dbpath) throws SqliteException {
        this.dbpath = dbpath;
        this.connection = dmConnection.connect(dbpath);
    }
    public String weatherCalculator(String checkIn, String checkOut, String island) {
        Map<String, Map<String, Double>> dataByZone = fetchWeatherData(checkIn, checkOut, island);
        String tablename = "prediction_Weather";
        if (dataByZone.isEmpty()) {
            return "No weather data currently available, please try again.";
        }
        if (!isDateInTable(checkIn) && !isDateInTable(checkOut)) {
            return "No weather data available for any of the selected dates.";
        } else if (!isDateInTable(checkIn)) {
            return "No weather data available for the provided check-in date. Please choose a different check-in date.";
        } else if (!isDateInTable(checkOut)) {
            return "No weather data available for the provided check-out date. Please choose a different check-out date.";
        }

        return buildResult(dataByZone);
    }
    private Map<String, Map<String, Double>> fetchWeatherData(String checkIn, String checkOut, String island) {
        String sql = "SELECT island, zone, AVG(temperature) AS avg_temperature, AVG(precipitation) AS avg_precipitation, AVG(clouds) AS avg_clouds FROM prediction_Weather WHERE predictionTime BETWEEN ? AND ? AND island = ? GROUP BY island, zone";
        Map<String, Map<String, Double>> dataByZone = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, checkIn);
            preparedStatement.setString(2, checkOut);
            preparedStatement.setString(3, island);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String zone = resultSet.getString("zone");
                    double avgTemperature = resultSet.getDouble("avg_temperature");
                    double avgPrecipitation = resultSet.getDouble("avg_precipitation");
                    double avgClouds = resultSet.getDouble("avg_clouds");

                    dataByZone.computeIfAbsent(zone, k -> new HashMap<>())
                            .put("avgTemperature", avgTemperature);
                    dataByZone.get(zone).put("avgPrecipitation", avgPrecipitation);
                    dataByZone.get(zone).put("avgClouds", avgClouds);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error processing weather data.");
        }

        return dataByZone;
    }

    private String buildResult(Map<String, Map<String, Double>> dataByZone) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Pattern for two decimals
        StringBuilder weatherResult = new StringBuilder();

        dataByZone.forEach((zone, averagesByZone) ->
                weatherResult.append("Zone: ").append(zone)
                        .append(", Average temperature: ").append(decimalFormat.format(averagesByZone.get("avgTemperature")))
                        .append(" (").append(classifyTemperature(averagesByZone.get("avgTemperature"))).append(")")
                        .append(", Average precipitation: ").append(decimalFormat.format(averagesByZone.get("avgPrecipitation")))
                        .append(" (").append(classifyPrecipitation(averagesByZone.get("avgPrecipitation"))).append(")")
                        .append(", Average clouds: ").append(decimalFormat.format(averagesByZone.get("avgClouds")))
                        .append(" (").append(classifyClouds(averagesByZone.get("avgClouds"))).append(")\n")
        );

        return weatherResult.toString();
    }

    private boolean isDateInTable(String date) {
        String sql = "SELECT COUNT(*) FROM prediction_Weather WHERE predictionTime = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, date);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String classifyTemperature(double temperature) {
        if (temperature >= 25.0) {
            return "Excellent";
        } else if (temperature >= 15.0) {
            return "Good";
        } else {
            return "Bad";
        }
    }
    private static String classifyClouds(double clouds) {
        if (clouds < 30.0) {
            return "Excellent";
        } else if (clouds < 70.0) {
            return "Good";
        } else {
            return "Bad";
        }
    }
    private static String classifyPrecipitation(double precipitation) {
        if (precipitation < 5.0) {
            return "Excellent";
        } else if (precipitation <  15.0) {
            return "Good";
        } else {
            return "Bad";
        }
    }
}
