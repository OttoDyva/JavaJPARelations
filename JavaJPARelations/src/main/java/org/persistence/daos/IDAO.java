package org.persistence.daos;

public interface IDAO <T>{
    void create(T t);

    T getByID(Long id);

    void update(T t);
}
