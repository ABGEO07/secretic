package dev.abgeo.secretic.service;

import dev.abgeo.secretic.model.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

}
