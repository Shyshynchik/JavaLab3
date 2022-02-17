package mysql.dao;

import java.util.List;

public interface DAO<T> {
    T getById(Integer id);
    List<T> getAll();
}
