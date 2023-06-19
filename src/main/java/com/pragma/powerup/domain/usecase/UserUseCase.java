package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveUser(UserModel userModel) {
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public boolean findOwnerById(Long id) {
        return userPersistencePort.findOwnerById(id);
    }

    @Override
    public String findPhoneById(Long id) {
        return userPersistencePort.findUserPhoneById(id);
    }
}
