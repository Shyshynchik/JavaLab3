package mysql.dao;

import device.impl.TabletImpl;

import java.util.List;

public interface DAOTablet<T> extends DAO<T> {
    T add(String brand, Double diagonal, Integer ram, String os, Integer memory);
    T add(TabletImpl tabletImpl);
    List<T> find(String brand, String os);
}
