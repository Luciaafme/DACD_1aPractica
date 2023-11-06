package practica1_dacd_afonso_medina;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    public String readFile(String filename) {
        String value = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            value = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
