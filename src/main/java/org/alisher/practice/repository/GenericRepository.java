package org.alisher.practice.repository;

import java.util.Collection;
import java.util.List;
import java.util.function.ToLongFunction;

public interface GenericRepository<T, ID>{

    T save(T entity);
    T getById(ID id);
    List<T> getAll();
    T update(T entity);
    void deleteById(ID id);

    default Long generateNextId(
            Collection<T> entities,
            ToLongFunction<T> getId
    ) {
        return entities.stream()
                .mapToLong(getId)
                .max()
                .orElse(0L) + 1;
    }
}
