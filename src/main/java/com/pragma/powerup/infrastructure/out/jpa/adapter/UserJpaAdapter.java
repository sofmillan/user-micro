package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.exception.DataAlreadyExistsException;
import com.pragma.powerup.infrastructure.exception.DataNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel saveUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        if(roleRepository.findById(userModel.getRole()).isEmpty()){
            throw new DataNotFoundException();
        }
        if(userRepository.findByEmail(userModel.getEmail()).isPresent()){
            throw new DataAlreadyExistsException();
        }
        userEntity.setRoleEntity(roleRepository.findById(userModel.getRole()).get());
        userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(userEntity);
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public boolean findOwnerById(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty() || optionalUser.get().getRoleEntity().getId()!=2){
            return false;
        }
        return true;
    }

    @Override
    public String findUserPhoneById(Long clientId) {
        UserEntity user = userRepository.findById(clientId).orElseThrow(DataNotFoundException::new);
        return user.getPhoneNumber();
    }
}
