package com.github.wesleyvlk.storesimulator.shared.controller;

import com.github.wesleyvlk.storesimulator.shared.service.SharedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

public interface SharedController<T, DTO> {

    ResponseEntity<Object> create(DTO dto);

    ResponseEntity<Object> findById(UUID id);

    ResponseEntity<Object> update(UUID id, DTO dto);

    ResponseEntity<Object> delete(UUID id);

    default <ENTITY_SERVICE> ENTITY_SERVICE foundRuleOfThree(UUID id, SharedService<ENTITY_SERVICE> service) {
        Optional<ENTITY_SERVICE> object = service.findById(id);
        if (!object.isPresent()) {
            String objectName = object.map(Object::getClass).map(Class::getSimpleName).orElse("Object");
            var message = String.format("%s not found.", objectName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }
        return object.get();
    }
}
