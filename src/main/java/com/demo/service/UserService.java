package com.demo.service;

import com.demo.dto.RegisterSellerRequestDTO;
import com.demo.dto.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(RegisterSellerRequestDTO registerSellerRequestDTO);
    List<UserDTO> findAllUsers();
    UserDTO findUserById(Integer id);
    void deleteUser(Integer id);
}
