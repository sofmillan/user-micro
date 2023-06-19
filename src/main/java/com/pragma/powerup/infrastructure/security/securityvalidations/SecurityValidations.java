package com.pragma.powerup.infrastructure.security.securityvalidations;

import com.pragma.powerup.infrastructure.exception.DataNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.powerup.infrastructure.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SecurityValidations implements ISecurityValidations{
    private final IUserRepository userRepository;


    @Override
    public List<String> findToken(String token) {
        String newtoken = token.replace("Bearer ","");
        UsernamePasswordAuthenticationToken psw = TokenUtils.getAuthentication(newtoken);
        if(psw==null){
            return Collections.emptyList();
        }
        System.out.println(psw.getAuthorities().stream().map(rol-> rol.getAuthority()).collect(Collectors.toList()));
        return psw.getAuthorities().stream().map(rol-> rol.getAuthority()).collect(Collectors.toList());
    }

    @Override
    public Long finOwner(String token) {
        String newtoken = token.replace("Bearer ","");
        UsernamePasswordAuthenticationToken psw = TokenUtils.getAuthentication(newtoken);
        if(psw==null){
            return null;
        }
        String email = (String) psw.getPrincipal();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new DataNotFoundException());
        List<String> list = psw.getAuthorities().stream()
                .map(rol-> rol.getAuthority()).collect(Collectors.toList());
        if(list.contains("ROLE_OWNER")){
            return user.getId();
        }
        return null;
    }

    @Override
    public Long findClientId(String token) {
        String newtoken = token.replace("Bearer ","");
        UsernamePasswordAuthenticationToken psw = TokenUtils.getAuthentication(newtoken);
        if(psw==null){
            return null;
        }
        String email = (String) psw.getPrincipal();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new DataNotFoundException());
        List<String> list = psw.getAuthorities().stream()
                .map(rol-> rol.getAuthority()).collect(Collectors.toList());
        if(list.contains("ROLE_CLIENT")){
            return user.getId();
        }
        return null;
    }

    @Override
    public Long findEmployeeId(String token) {
        String newtoken = token.replace("Bearer ","");
        UsernamePasswordAuthenticationToken psw = TokenUtils.getAuthentication(newtoken);
        if(psw==null){
            return null;
        }
        String email = (String) psw.getPrincipal();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new DataNotFoundException());
        List<String> list = psw.getAuthorities().stream()
                .map(rol-> rol.getAuthority()).collect(Collectors.toList());
        if(list.contains("ROLE_EMPLOYEE")){
            return user.getId();
        }
        return null;
    }
}
