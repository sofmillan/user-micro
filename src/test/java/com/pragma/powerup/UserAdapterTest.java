package com.pragma.powerup;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.exception.DataAlreadyExistsException;
import com.pragma.powerup.infrastructure.exception.DataNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserAdapterTest {

  IUserRepository userRepository;
  IRoleRepository roleRepository;
  IUserPersistencePort userPersistence;
  IUserEntityMapper userEntityMapper;
  PasswordEncoder passwordEncoder;
  UserModel user;

  @BeforeEach
  void setUp(){
      roleRepository = mock(IRoleRepository.class);
      userRepository = mock(IUserRepository.class);
      passwordEncoder = mock(PasswordEncoder.class);
      userEntityMapper =mock(IUserEntityMapper.class);
      userPersistence = new UserJpaAdapter(userRepository, roleRepository, userEntityMapper, passwordEncoder);
      user = new UserModel();
      user.setDni("999");
      user.setName("Ricky");
      user.setLastName("Shen");
      user.setEmail("ricky@gmail.com");
      user.setPassword("password");
      user.setRole(4L);
      user.setPhoneNumber("+573234568912");
  }

  @Test
   void Should_ThrowException_When_EmailAlreadyRegistered(){
      when(roleRepository.findById(user.getRole())).thenReturn(Optional.of(new RoleEntity()));
      when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(new UserEntity()));

      assertThrows(DataAlreadyExistsException.class, () -> userPersistence.saveUser(user));
  }

    @Test
    void Should_ThrowException_When_RoleIdDoesNotExist(){
        when(roleRepository.findById(user.getRole())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userPersistence.saveUser(user));
    }

    @Test
    void Should_ThrowException_When_OwnerIdNotFound(){
      Long searchId = 1L;
      when(userRepository.findById(searchId)).thenReturn(Optional.empty());

      assertFalse(userPersistence.findOwnerById(searchId));
    }

    @Test
    void Should_ThrowException_When_OwnerIdNotRelated(){
        Long searchId = 1L;
        UserEntity userEntity = new UserEntity();
        RoleEntity roleEntity= new RoleEntity(3L,"Employee","Description");

        userEntity.setRoleEntity(roleEntity);
        when(userRepository.findById(searchId)).thenReturn(Optional.of(userEntity));
        assertFalse(userPersistence.findOwnerById(searchId));
    }
}
