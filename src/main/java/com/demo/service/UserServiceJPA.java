package com.demo.service;

import com.demo.dto.RegisterRequestDTO;
import com.demo.dto.UserDTO;
import com.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceJPA {
    User saveUser(RegisterRequestDTO registerRequestDTO);
    List<UserDTO> findAllUsers();
    Optional<UserDTO> findUserById(Integer id);
    void deleteUser(Integer id);
}
