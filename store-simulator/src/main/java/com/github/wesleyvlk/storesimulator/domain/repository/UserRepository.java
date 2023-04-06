package com.github.wesleyvlk.storesimulator.domain.repository;

import com.github.wesleyvlk.storesimulator.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByCpf(String cpf);
}
