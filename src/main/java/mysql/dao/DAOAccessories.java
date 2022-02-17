package mysql.dao;

public interface DAOAccessories<T> extends DAO<T> {
    T getByNameOrAdd(String name);
    T add(String name);
}
