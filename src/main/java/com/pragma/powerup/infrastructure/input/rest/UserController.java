package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.infrastructure.security.securityvalidations.ISecurityValidations;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;


@Tag(name="Users", description = "User related operations")
@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final IUserHandler userHandler;
    private final ISecurityValidations securityValidations;

    @Operation(summary = "Create a new owner",
            description = "As an administrator, you can register a new owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "409", description = "Owner already exists", content = @Content)
    })
    @PostMapping("/owner")
    public ResponseEntity<Void>  saveOwner(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Owner's information")
                                            @RequestBody @Valid  UserRequestDto userRequestDto){
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Create a new employee",
            description = "As an owner, you can register a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists", content = @Content)
    })
    @PostMapping("/employee")
    public ResponseEntity<Void>  saveEmployee(@Parameter(description = "Employee's information")
                                              @RequestBody @Valid  UserRequestDto userRequestDto){
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Create a new client",
            description = "As a client, you can create an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Some data is not valid, check it", content = @Content),
            @ApiResponse(responseCode = "409", description = "Client already exists", content = @Content)
    })
    @PostMapping("/client")
    public ResponseEntity<Void>  saveClient(@Parameter(description = "Client's information")
                                            @RequestBody @Valid  UserRequestDto userRequestDto){
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clientPhone/{clientId}")
    public String findPhone(@PathVariable Long clientId){
         return userHandler.findPhoneById(clientId);
    }


    @Hidden
    @GetMapping("owner/{id}")
    public boolean findOwner(@PathVariable Long id){
        return userHandler.findOwnerById(id);
    }

    @Hidden
    @GetMapping("token/{token}")
    public List<String> findToken(@PathVariable String token){
        return securityValidations.findToken(token);
    }

    @Hidden
    @GetMapping("rightOwner/{token}")
    public Long finOwner(@PathVariable String token){
        return securityValidations.finOwner(token);
    }

    @Hidden
    @GetMapping("client/{token}")
    public Long findClientId(@PathVariable String token){
        return securityValidations.findClientId(token);
    }

    @Hidden
    @GetMapping("employee/{token}")
    public Long findEmployeeId(@PathVariable String token){
        return securityValidations.findEmployeeId(token);
    }
//
}
