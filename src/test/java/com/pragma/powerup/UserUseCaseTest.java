package com.pragma.powerup;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    IUserPersistencePort userPersistence;
    IUserServicePort userService;
    UserModel userModel;

    @BeforeEach
    void setUp(){
        userPersistence = mock(UserJpaAdapter.class);
        userService = new UserUseCase(userPersistence);

        userModel = new UserModel();
        userModel.setDni("999");
        userModel.setName("Ricky");
        userModel.setLastName("Shen");
        userModel.setEmail("ricky@gmail.com");
        userModel.setPassword("password");
        userModel.setRole(4L);
        userModel.setPhoneNumber("+573234568912");
    }
    @Test
    void Should_SaveUser(){
        userService.saveUser(userModel);

        verify(userPersistence).saveUser(userModel);
    }

    @Test
    void Should_ReturnTrue_When_FindOwnerById(){
        Long userId = 1L;

        when(userPersistence.findOwnerById(userId)).thenReturn(true);

        assertTrue(userService.findOwnerById(userId));
    }

    @Test
    void Should_ReturnFalse_When_OwnerNotFound(){
        Long userId = 1L;

        when(userPersistence.findOwnerById(userId)).thenReturn(false);

        assertFalse(userService.findOwnerById(userId));
    }

    @Test
    void Should_ReturnString_When_PhoneNumberFound(){
        Long userId = 1L;
        String expectedPhoneNumber = "+573234568912";
        when(userPersistence.findUserPhoneById(userId)).thenReturn(userModel.getPhoneNumber());

        assertEquals(expectedPhoneNumber, userService.findPhoneById(userId));
    }
}
