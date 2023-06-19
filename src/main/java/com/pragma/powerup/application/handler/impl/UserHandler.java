package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;

import com.pragma.powerup.application.mapper.IUserRequestMapper;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {


    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toUser(userRequestDto);
        userServicePort.saveUser(userModel);
    }

    @Override
    public boolean findOwnerById(Long id) {
       return userServicePort.findOwnerById(id);
    }

    @Override
    public String findPhoneById(Long id) {
        return userServicePort.findPhoneById(id);
    }
}
