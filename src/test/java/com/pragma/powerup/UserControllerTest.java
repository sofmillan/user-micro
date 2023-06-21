package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.handler.impl.UserHandler;
import com.pragma.powerup.infrastructure.input.rest.UserController;
import com.pragma.powerup.infrastructure.security.securityvalidations.ISecurityValidations;
import com.pragma.powerup.infrastructure.security.securityvalidations.SecurityValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class UserControllerTest {
    IUserHandler userHandler;
    ISecurityValidations securityValidations;
    UserController userController;
    UserRequestDto userRequestDto;

    @BeforeEach
    void setUp(){
        userHandler = mock(UserHandler.class);
        securityValidations = mock(SecurityValidations.class);
        userController = new UserController(userHandler, securityValidations);
    }

    @Test
    void Should_ReturnResponseEntityCreated_When_OwnerSaved(){
        userRequestDto = new UserRequestDto();
        userRequestDto.setDni("999");
        userRequestDto.setName("Ricky");
        userRequestDto.setLastName("Shen");
        userRequestDto.setEmail("ricky@gmail.com");
        userRequestDto.setPassword("password");
        userRequestDto.setRole(2L);
        userRequestDto.setPhoneNumber("+573234568912");

        doNothing().when(userHandler).saveUser(userRequestDto);

        ResponseEntity<Void> responseEntity = userController.saveOwner(userRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void Should_ReturnResponseEntityCreated_When_EmployeeSaved(){
        userRequestDto = new UserRequestDto();
        userRequestDto.setDni("999");
        userRequestDto.setName("Ricky");
        userRequestDto.setLastName("Shen");
        userRequestDto.setEmail("ricky@gmail.com");
        userRequestDto.setPassword("password");
        userRequestDto.setRole(3L);
        userRequestDto.setPhoneNumber("+573234568912");

        doNothing().when(userHandler).saveUser(userRequestDto);

        ResponseEntity<Void> responseEntity = userController.saveEmployee(userRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void Should_ReturnResponseEntityCreated_When_ClientSaved(){
        userRequestDto = new UserRequestDto();
        userRequestDto.setDni("999");
        userRequestDto.setName("Ricky");
        userRequestDto.setLastName("Shen");
        userRequestDto.setEmail("ricky@gmail.com");
        userRequestDto.setPassword("password");
        userRequestDto.setRole(3L);
        userRequestDto.setPhoneNumber("+573234568912");

        doNothing().when(userHandler).saveUser(userRequestDto);

        ResponseEntity<Void> responseEntity = userController.saveClient(userRequestDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}
