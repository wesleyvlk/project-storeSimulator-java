package com.github.wesleyvlk.storesimulator.shared.service;

import java.util.Optional;
import java.util.UUID;

public interface SharedService<T> {

    T create(T object);

    Optional<T> findById(UUID id);

    T update(T object);

    void delete(UUID id);

}
