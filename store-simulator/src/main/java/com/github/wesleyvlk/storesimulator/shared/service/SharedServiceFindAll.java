package com.github.wesleyvlk.storesimulator.shared.service;

public interface SharedServiceFindAll<T> extends SharedService<T> {

    Iterable<T> findAll();

}
