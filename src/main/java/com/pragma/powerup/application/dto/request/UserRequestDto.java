package com.pragma.powerup.application.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotNull(message = "Dni must not be null")
    @NotBlank(message = "Dni cannot be an empty string")
    @Pattern(regexp = "^[0-9]*$", message = "Dni must only have numbers")
    private String dni;

    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Name cannot be an empty string")
    private String name;

    @NotNull(message = "Last name must not be null")
    @NotBlank(message = "Last name cannot be an empty string")
    private String lastName;

    @NotNull(message = "Phone number must not be null")
    @NotBlank(message = "Phone number cannot be an empty string")
    @Pattern(regexp = "^(\\(?\\+?[0-9]{2,12}\\)?)$", message = "Phone number is not valid")
    private String phoneNumber;

    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email cannot be an empty string")
    @Email(message="Email is not valid")
    private String email;

    @NotNull(message = "Password must not be null")
    @NotBlank(message = "Password cannot be an empty string")
    private String password;

    @NotNull(message = "Role id must not be null")
    private Long role;
}
