package csv_handler;

import device.impl.DeviceImpl;

import java.util.ArrayList;

public interface CSVHandler {
    String PATH = "src/main/java/files/smth.csv";
    String SPLITTER = ",";
    String PATH_SAVE_CSV = "src/main/java/files/smth1.csv";

    ArrayList<DeviceImpl> read();

    void saveToCsv(ArrayList<DeviceImpl> deviceArrayList);

    void logClose();
}
