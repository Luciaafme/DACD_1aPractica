package practica1_dacd_afonso_medina.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class EventWriter {

    public void insertEvent(String serializedObject) {
        Gson gson = new Gson();
        JsonObject event = gson.fromJson(serializedObject, JsonObject.class);
        String ss = event.get("ss").getAsString();
        String ts = event.get("ts").getAsString();
        String formattedTs = dateFormatter(ts);
        String directoryPath = "eventstore/prediction.Weather/" + ss;
        createDirectory(directoryPath);
        String filepath = directoryPath + "/" + formattedTs + ".events";
        writeToFile(filepath, serializedObject);
    }

    private String dateFormatter(String ts) {
        Instant instant = Instant.parse(ts);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return zonedDateTime.format(outputFormatter);
    }

    private void createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Directory created successfully: " + directory.getAbsolutePath());
            } else {
                System.err.println("The directory could not be created.");
            }
        } else {
            System.out.println("The directory already exists: " + directory.getAbsolutePath());
        }
    }

    private void writeToFile(String filepath, String serializedObject) {
        try (FileWriter escritor = new FileWriter(filepath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(escritor)) {
            bufferedWriter.write(serializedObject);
            bufferedWriter.newLine();
            System.out.println("Data successfully written to: " + filepath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

