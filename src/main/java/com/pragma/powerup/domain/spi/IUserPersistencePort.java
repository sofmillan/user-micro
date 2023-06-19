package com.pragma.powerup.domain.spi;


import com.pragma.powerup.domain.model.UserModel;

public interface IUserPersistencePort {
    UserModel saveUser(UserModel userModel);

    boolean findOwnerById(Long id);

    String findUserPhoneById(Long clientId);
}
