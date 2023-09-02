package ru.richie.repositories;

import java.util.List;

public interface GenericRepo<T,ID>{
    boolean add(T t);

    T getById(ID id);

    List<T> getAll();

    T update(T t);

    boolean deleteById(ID id);
}
