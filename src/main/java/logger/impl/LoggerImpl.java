package logger.impl;

import logger.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerImpl implements Logger {

    public String PATH = "src/main/java/logger/log.txt";

    private FileWriter writer = null;


    public LoggerImpl() {
        try {
            File file = new File(PATH);
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSuccessLog(String message, String info) {
        try {
            writer.write("!Success " + getDate() + " " + message + " Подробнее: " + info + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createErrorLog(String message, Exception error) {
        try {
            writer.write("!Error   " + getDate() + " " + message + " Подробнее: " + error + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createInfoLog(String message) {
        try {
            writer.write("!Info    " + getDate() + " " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy H:m:s");
        return dateFormat.format(date);
    }

    @Override
    public void logClose() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
