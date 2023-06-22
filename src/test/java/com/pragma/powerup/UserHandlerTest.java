package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.handler.impl.UserHandler;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.usecase.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserHandlerTest {
    IUserServicePort userService;
    IUserRequestMapper userRequestMapper;
    IUserHandler userHandler;
    UserRequestDto userRequestDto;
    UserModel userModel;

    @BeforeEach
    void setUp(){
        userService = mock(UserUseCase.class);
        userRequestMapper = mock(IUserRequestMapper.class);
        userHandler = new UserHandler(userService, userRequestMapper);
    }

    @Test
    void Should_SaveUser(){
        userRequestDto = new UserRequestDto();
        userRequestDto.setDni("999");
        userRequestDto.setName("Ricky");
        userRequestDto.setLastName("Shen");
        userRequestDto.setEmail("ricky@gmail.com");
        userRequestDto.setPassword("password");
        userRequestDto.setRole(4L);
        userRequestDto.setPhoneNumber("+573234568912");

        userModel = new UserModel();
        userModel.setDni("999");
        userModel.setName("Ricky");
        userModel.setLastName("Shen");
        userModel.setEmail("ricky@gmail.com");
        userModel.setPassword("password");
        userModel.setRole(4L);
        userModel.setPhoneNumber("+573234568912");

        when(userRequestMapper.toUser(userRequestDto)).thenReturn(userModel);

        userHandler.saveUser(userRequestDto);

        verify(userService).saveUser(userModel);
    }

    @Test
    void Should_ReturnTrue_When_OwnerIdFound(){
        Long userId = 1L;
        when(userService.findOwnerById(userId)).thenReturn(true);

        assertTrue(userHandler.findOwnerById(userId));
    }

    @Test
    void Should_ReturnFalse_When_OwnerIdNotFound(){
        Long userId = 2L;

        when(userService.findOwnerById(userId)).thenReturn(false);

        assertFalse(userHandler.findOwnerById(userId));
    }

    @Test
    void Should_ReturnPhoneNumber_When_UserFound(){
        Long userId = 1L;
        userRequestDto = new UserRequestDto();
        userRequestDto.setPhoneNumber("+573234568912");
        String expectedPhoneNumber = "+573234568912";

        when(userService.findPhoneById(userId)).thenReturn(userRequestDto.getPhoneNumber());

        assertEquals(expectedPhoneNumber, userHandler.findPhoneById(userId));
    }
}
