package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerService {
    private static final String LOG_FILE_PATH = "application.log";
    public void info(String message) {
        log("INFO", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }

    private void log(String level, String message) {
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        String logMessage = String.format("%s [%s] %s%n", timestamp, level, message);

        try (FileWriter writer = new FileWriter(LOG_FILE_PATH, true)) {
            writer.write(logMessage);
        } catch (IOException e) {
            System.out.println("Помилка при запису логів: " + e.getMessage());
        }
    }
}
