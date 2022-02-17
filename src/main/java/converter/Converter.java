package converter;

import csv_handler.CSVHandler;
import csv_handler.Impl.CSVHandlerImpl;
import device.impl.DeviceImpl;
import device.impl.LaptopImpl;
import device.impl.TabletImpl;
import mysql.dao.DAOLaptop;
import mysql.dao.DAOTablet;
import mysql.entity.Laptop;
import mysql.entity.Tablet;
import mysql.service.LaptopService;
import mysql.service.TabletService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    private CSVHandler csvHandler;
    private ArrayList<DeviceImpl> deviceArrayList;
    private final DAOTablet<Tablet> tabletService;
    private final DAOLaptop<Laptop> laptopService;

    public Converter(Connection connection) {
        this.tabletService = new TabletService(connection);
        this.laptopService = new LaptopService(connection);
    }

    public void setCsvHandler() {
        csvHandler = new CSVHandlerImpl();
        deviceArrayList = csvHandler.read();
    }

    public void fromCsvToSQL() {
        for (DeviceImpl el: deviceArrayList) {
            if (el.getType().equals("tablet")) {
                addTablet((TabletImpl) el);
            } else {
                addLaptop((LaptopImpl) el);
            }
        }
    }

    private void addTablet(TabletImpl tablet) {
        tabletService.add(tablet);
    }

    private void addLaptop(LaptopImpl laptop) {
        laptopService.add(laptop);
    }

    public void fromSQLToCSV() {
        csvHandler.saveToCsv(fromSqlToArray());
    }

    private ArrayList<DeviceImpl> fromSqlToArray() {
        ArrayList<DeviceImpl> devices = new ArrayList<>();
        devices.addAll(getLaptopSQL());
        devices.addAll(getTabletSQL());
        return devices;
    }

    private ArrayList<TabletImpl> getTabletSQL() {
        List<Tablet> tablets = tabletService.getAll();
        ArrayList<TabletImpl> arrayList = new ArrayList<>();

        for (Tablet tablet : tablets) {
            arrayList.add(convertTablet(tablet));
        }
        return arrayList;
    }

    private TabletImpl convertTablet(Tablet tablet) {
        TabletImpl tableImpl = new TabletImpl();

        tableImpl.setData(tablet.convert());

        return tableImpl;
    }

    private ArrayList<LaptopImpl> getLaptopSQL() {
        List<Laptop> laptops = laptopService.getAll();
        ArrayList<LaptopImpl> arrayList = new ArrayList<>();

        for (Laptop laptop : laptops) {
            arrayList.add(convertLaptop(laptop));
        }
        return arrayList;
    }

    private LaptopImpl convertLaptop(Laptop laptop) {
        LaptopImpl laptopImpl = new LaptopImpl();

        laptopImpl.setData(laptop.convert());

        return laptopImpl;
    }

    public void logClose() {
        csvHandler.logClose();
    }
}
