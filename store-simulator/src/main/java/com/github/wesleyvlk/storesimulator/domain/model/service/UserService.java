package com.github.wesleyvlk.storesimulator.domain.model.service;

import com.github.wesleyvlk.storesimulator.domain.model.User;

public interface UserService {

    User save(User user);

    User findByCpf(String cpf);
}
