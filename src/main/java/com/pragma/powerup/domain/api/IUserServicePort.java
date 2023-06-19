package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserServicePort {
    void saveUser(UserModel userModel);

    boolean findOwnerById(Long id);

    String findPhoneById(Long id);
}
