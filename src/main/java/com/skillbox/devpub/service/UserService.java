package com.skillbox.devpub.service;

import com.skillbox.devpub.model.User;

public interface UserService {

    User register(User user);

    User findByEmail(String email);

    User findById(Integer id);
}
