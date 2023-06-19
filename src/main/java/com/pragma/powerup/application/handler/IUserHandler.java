package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.UserRequestDto;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);

    boolean findOwnerById(Long id);

    String findPhoneById(Long id);
}
