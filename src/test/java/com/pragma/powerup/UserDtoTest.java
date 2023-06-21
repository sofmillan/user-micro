package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void When_DataIsValid_Expect_NoViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","+576542",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void When_DniHasLetters_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123aaa","Sofia","Millan","+576542",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_PhoneNumberMoreThan13Digits_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","+57654444444442",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_EmailNotValid_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","+3234896894",
                "sofiagmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        assertEquals(1, violations.size());
    }

    @Test
    void When_DniIsNull_Expect_TwoViolation(){
        UserRequestDto userDto = new UserRequestDto(null,"Sofia","Millan","+3234896894",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);

        assertEquals(2, violations.size());
    }

    @Test
    void When_NameIsNull_Expect_TwoViolation(){
        UserRequestDto userDto = new UserRequestDto("123",null,"Millan","+3234896894",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(2, violations.size());
    }

    @Test
    void When_LastNameIsNull_Expect_TwoViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia",null,"+3234896894",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(2, violations.size());
    }

    @Test
    void When_PhoneNumberIsNull_Expect_TwoViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan",null,
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(2, violations.size());
    }

    @Test
    void When_EmailIsNull_Expect_TwoViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","548454845",
                null,"123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(2, violations.size());
    }

    @Test
    void When_PasswordIsNull_Expect_TwoViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","548454845",
                "sofia@gmail.com",null,101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(2, violations.size());
    }

    @Test
    void When_RoleIsNull_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","548454845",
                "sofia@gmail.com","123",null);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(1, violations.size());
    }

    @Test
    void When_DniIsEmpty_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("","Sofia","Millan","548454845",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(1, violations.size());
    }

    @Test
    void When_NameIsEmpty_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123","","Millan","548454845",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(1, violations.size());
    }
    @Test
    void When_LastNameIsEmpty_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","","548454845",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(1, violations.size());
    }

    @Test
    void When_PhoneNumberIsEmpty_Expect_TwoViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","",
                "sofia@gmail.com","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(2, violations.size());
    }


    @Test
    void When_EmailIsEmpty_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","548454845",
                "","123",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(1, violations.size());
    }
    @Test
    void When_PasswordIsEmpty_Expect_OneViolation(){
        UserRequestDto userDto = new UserRequestDto("123","Sofia","Millan","548454845",
                "sofia@gmail.com","",101L);

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        System.out.println(violations);
        assertEquals(1, violations.size());
    }


}
