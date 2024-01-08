package practica1_dacd_afonso_medina.view;

import practica1_dacd_afonso_medina.control.DmConnection;
import practica1_dacd_afonso_medina.control.exception.SqliteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class BookingCalculator {
    private Connection connection;
    private DmConnection dmConnection;
    private String dbpath;

    public BookingCalculator(String dbpath) throws SqliteException {
        this.dbpath = dbpath;
        this.connection = dmConnection.connect(dbpath);
    }

    public String bookingCalculator(String checkIn, String checkOut, String island) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime noon = LocalTime.of(12, 0);
        String sql = "SELECT island, zone, hotel_name, platform, price AS price FROM prediction_Booking WHERE check_in = ? AND island = ? GROUP BY island, zone, hotel_name, platform";
        StringBuilder bookingResult = new StringBuilder();
        boolean dataAvailable = fetchDataForBooking(sql, checkIn, checkOut, island, bookingResult);
        if (isAfterNoonToday(checkIn, currentDateTime, noon)) {
            return "Booking for today will be made for the next day, as it is after 12:00:00.";
        }
        LocalDateTime maxAllowedDateTime = calculateMaxAllowedDateTime(currentDateTime, noon);
        if (isCheckOutAfterMaxAllowedDateTime(checkOut, maxAllowedDateTime)) {
            return "Sorry, booking is limited to a maximum of 5 days, i.e., 4 nights from today or tomorrow (if the booking is after 12:00:00).";
        }

        if (!dataAvailable) {
            return "Keep trying. Currently, there is no available data for the booking.";
        }

        return bookingResult.toString();
    }
    private boolean isAfterNoonToday(String checkIn, LocalDateTime currentDateTime, LocalTime noon) {
        return checkIn.equals(currentDateTime.toLocalDate().toString()) && currentDateTime.toLocalTime().isAfter(noon);
    }

    private LocalDateTime calculateMaxAllowedDateTime(LocalDateTime currentDateTime, LocalTime noon) {
        LocalDateTime maxAllowedDateTime = currentDateTime.toLocalDate().atTime(23, 59, 59);
        if (currentDateTime.toLocalTime().isAfter(noon)) {
            maxAllowedDateTime = maxAllowedDateTime.plusDays(1);
        }
        return maxAllowedDateTime.plusDays(4);
    }

    private boolean isCheckOutAfterMaxAllowedDateTime(String checkOut, LocalDateTime maxAllowedDateTime) {
        return LocalDateTime.parse(checkOut + "T23:59:59").isAfter(maxAllowedDateTime);
    }

    private boolean fetchDataForBooking(String sql, String checkIn, String checkOut, String island, StringBuilder bookingResult) {
        boolean dataAvailable = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, checkIn);
            preparedStatement.setString(2, island);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    dataAvailable = true;
                    String zone = resultSet.getString("zone");
                    String hotel = resultSet.getString("hotel_name");
                    String platform = resultSet.getString("platform");
                    double price = resultSet.getDouble("price");

                    LocalDate checkInDay = LocalDate.parse(checkIn);
                    LocalDate checkOutDay = LocalDate.parse(checkOut);
                    long nights = ChronoUnit.DAYS.between(checkInDay, checkOutDay);

                    double totalPrice = price * nights;

                    bookingResult.append("Zone: ").append(zone)
                            .append(", Hotel: ").append(hotel)
                            .append(", Platform: ").append(platform)
                            .append(", Total price for ").append(nights).append(" nights: ").append(totalPrice).append("\n");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error processing booking data. Please try again later.");
        }
        return dataAvailable;
    }
}
