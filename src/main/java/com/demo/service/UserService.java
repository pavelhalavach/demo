package com.demo.service;

import com.demo.dto.RegisterSellerRequestDTO;
import com.demo.dto.UserDTO;
import com.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void saveUser(RegisterSellerRequestDTO registerSellerRequestDTO);
    List<UserDTO> findAllUsers();
    Optional<UserDTO> findUserById(Integer id);
    void deleteUser(Integer id);
}
