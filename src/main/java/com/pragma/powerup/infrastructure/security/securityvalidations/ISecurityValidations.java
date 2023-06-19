package com.pragma.powerup.infrastructure.security.securityvalidations;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ISecurityValidations {
    List<String> findToken(String token);

    Long finOwner( String token);

    Long findClientId(String token);
    Long findEmployeeId(String token);
}
