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


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserAdapterTest {

  IUserRepository userRepository;
  IRoleRepository roleRepository;
  IUserPersistencePort userPersistence;
  IUserEntityMapper userEntityMapper;
  PasswordEncoder passwordEncoder;
  UserModel userModel;
  UserEntity userEntity;
  RoleEntity roleEntity;

  @BeforeEach
  void setUp(){
      roleRepository = mock(IRoleRepository.class);
      userRepository = mock(IUserRepository.class);
      passwordEncoder = mock(PasswordEncoder.class);
      userEntityMapper =mock(IUserEntityMapper.class);
      userPersistence = new UserJpaAdapter(userRepository, roleRepository, userEntityMapper, passwordEncoder);

      userModel = new UserModel();
      userModel.setDni("999");
      userModel.setName("Ricky");
      userModel.setLastName("Shen");
      userModel.setEmail("ricky@gmail.com");
      userModel.setPassword("password");
      userModel.setRole(4L);
      userModel.setPhoneNumber("+573234568912");

      roleEntity = new RoleEntity();
      roleEntity.setName("ROLE_CLIENT");
      roleEntity.setId(4L);
      roleEntity.setDescription("Client's description");

      userEntity = new UserEntity();
      userEntity.setId(1L);
      userEntity.setDni(userModel.getDni());
      userEntity.setName(userModel.getName());
      userEntity.setLastName(userModel.getLastName());
      userEntity.setEmail(userModel.getEmail());
      userEntity.setPassword(userModel.getPassword());
      userEntity.setPhoneNumber(userModel.getPhoneNumber());

  }

  @Test
   void Should_ThrowException_When_EmailAlreadyRegistered(){
      when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);
      when(roleRepository.findById(userModel.getRole())).thenReturn(Optional.of(roleEntity));
      when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(new UserEntity()));

      assertThrows(DataAlreadyExistsException.class, () -> userPersistence.saveUser(userModel));
  }

    @Test
    void Should_ThrowException_When_RoleIdDoesNotExist(){
        when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);
        when(roleRepository.findById(userModel.getRole())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userPersistence.saveUser(userModel));
    }

    @Test
    void Should_Save_User(){
        when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);
        when(userEntityMapper.toUserModel(userEntity)).thenReturn(userModel);
        when(roleRepository.findById(userModel.getRole())).thenReturn(Optional.of(roleEntity));
        when(userRepository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());

        userPersistence.saveUser(userModel);

        assertThat(userEntity.getRoleEntity().getId()).isEqualTo(roleEntity.getId());
        verify(passwordEncoder).encode(userModel.getPassword());
        verify(userRepository).save(userEntity);
    }

    @Test
    void Should_ReturnFalse_When_OwnerIdNotFound(){
      Long searchId = 1L;

      when(userRepository.findById(searchId)).thenReturn(Optional.empty());

      assertFalse(userPersistence.findOwnerById(searchId));
    }

    @Test
    void Should_ReturnFalse_When_OwnerIdNotOwner(){
        Long searchId = 1L;

        roleEntity= new RoleEntity(3L,"Employee","Description");
        userEntity.setRoleEntity(roleEntity);

        when(userRepository.findById(searchId)).thenReturn(Optional.of(userEntity));
        assertFalse(userPersistence.findOwnerById(searchId));
    }

    @Test
    void Should_ReturnTrue_When_OwnerIdFound(){
        Long searchId = 1L;
        roleEntity= new RoleEntity(2L,"Owner","Description");

        userEntity.setRoleEntity(roleEntity);

        when(userRepository.findById(searchId)).thenReturn(Optional.of(userEntity));
        assertTrue(userPersistence.findOwnerById(searchId));
    }

    @Test
    void Should_ThrowDataNotFoundException_When_ClientIdNotFound(){
        Long searchId = 10L;

        when(userRepository.findById(searchId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> userPersistence.findUserPhoneById(searchId));
    }

    @Test
    void Should_ReturnPhoneNumber_When_ClientIdFound(){
        Long searchId = userEntity.getId();
        String expectedPhoneNumber = "+573234568912";
        when(userRepository.findById(searchId)).thenReturn(Optional.of(userEntity));

        String phoneNumber = userPersistence.findUserPhoneById(searchId);

        assertEquals(expectedPhoneNumber, phoneNumber);
    }
}
