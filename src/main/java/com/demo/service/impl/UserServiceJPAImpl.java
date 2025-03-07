package com.demo.service.impl;

import com.demo.dto.SellerGameDTO;
import com.demo.dto.RegisterSellerRequestDTO;
import com.demo.dto.UserDTO;
import com.demo.entity.Role;
import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.service.SellerGameService;
import com.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceJPAImpl implements UserService {
    private final UserRepository userRepository;
    private final SellerGameService sellerGameService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJPAImpl(
            UserRepository userRepository,
            SellerGameService sellerGameService,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sellerGameService = sellerGameService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(RegisterSellerRequestDTO registerSellerRequestDTO) {
        User user = new User();
        user.setFirstName(registerSellerRequestDTO.firstName());
        user.setLastName(registerSellerRequestDTO.lastName());
        user.setEmail(registerSellerRequestDTO.email());
        user.setPassword(passwordEncoder.encode(registerSellerRequestDTO.password()));
        user.setRole(Role.valueOf(registerSellerRequestDTO.role()));
//        user.setVerified("ADMIN".equals(user.getRole().toString()));
        user.setVerified(false);
        user = userRepository.save(user);

        for (SellerGameDTO sellerGameDTO : registerSellerRequestDTO.games()) {
            sellerGameService.saveSellerGame(user, sellerGameDTO);
        }
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : userRepository.findAll()){
            usersDTO.add(new UserDTO(
                    user.getFirstName(),
                    user.getLastName(),
                    sellerGameService.findAllSellerGames(user)
            ));
        }
        return usersDTO;
    }

    @Override
    public UserDTO findUserById(Integer id) {
        return userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getFirstName(),
                        user.getLastName(),
                        sellerGameService.findAllSellerGames(user)
                )).orElseThrow(() -> new RuntimeException("Such game is not found"));
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
