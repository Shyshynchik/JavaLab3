package mysql.dao;

import device.impl.LaptopImpl;

import java.util.List;

public interface DAOLaptop<T> extends DAO<T> {
    T add(String brand, Double diagonal, Integer ram, String cpu, String videoCard);
    T add(LaptopImpl laptopImpl);
    List<T> find(String brand, String cpu, String videoCard);
}
